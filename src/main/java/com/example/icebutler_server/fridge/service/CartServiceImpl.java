package com.example.icebutler_server.fridge.service;

import com.example.icebutler_server.fridge.dto.assembler.CartAssembler;
import com.example.icebutler_server.fridge.dto.assembler.CartFoodAssembler;
import com.example.icebutler_server.fridge.dto.request.AddFoodToCartRequest;
import com.example.icebutler_server.fridge.dto.request.RemoveFoodFromCartRequest;
import com.example.icebutler_server.fridge.dto.response.CartResponse;
import com.example.icebutler_server.fridge.entity.Cart;
import com.example.icebutler_server.fridge.entity.CartFood;
import com.example.icebutler_server.fridge.entity.Food;
import com.example.icebutler_server.fridge.exception.CartNotFoundException;
import com.example.icebutler_server.fridge.repository.CartFoodRepository;
import com.example.icebutler_server.fridge.repository.CartRepository;
import com.example.icebutler_server.fridge.repository.FoodRepository;
import com.example.icebutler_server.global.dto.response.ResponseCustom;
import com.example.icebutler_server.user.entity.User;
import com.example.icebutler_server.user.exception.UserNotFoundException;
import com.example.icebutler_server.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class CartServiceImpl implements CartService{

    private final UserRepository userRepository;
    private final CartFoodRepository cartFoodRepository;
    private final CartRepository cartRepository;
    private final FoodRepository foodRepository;
    private final CartAssembler cartAssembler;
    private final CartFoodAssembler cartFoodAssembler;


    @Override
    public ResponseCustom<CartResponse> getFoodsFromCart(Long cartIdx, Long userIdx) {
        Cart cart = cartRepository.findById(cartIdx).orElseThrow(CartNotFoundException::new);
        return ResponseCustom.OK(CartResponse.toDto(cart));
    }

    @Transactional
    @Override
    public ResponseCustom<CartResponse> addFoodsToCart(
            Long cartIdx,
            AddFoodToCartRequest request,
            Long userIdx
    )
    {
        User user = userRepository.findByUserIdx(userIdx).orElseThrow(UserNotFoundException::new);
        Cart cart = cartRepository.findById(cartIdx).orElseThrow(CartNotFoundException::new);
        List<Food> foods = foodRepository.findAllByFoodIdxIn(request.getAddFoodIdxes());

        List<Long> foodsInNowCart = cart.getCartFoods().stream()
                .map((cr) -> cr.getFood().getFoodIdx()).collect(Collectors.toList());

        // cart와 food의 연관관계를 맺어주는 cartFood 객체 생성
        List<CartFood> cartFoods = foods.stream()
                .filter((f)->{ // 이미 장바구니에 존재하는 음식은 거르기
                    for (Long foodInIdx : foodsInNowCart) if(foodInIdx.equals(f.getFoodIdx())) return false;
                    return true;
                })
                .map((food) -> cartFoodAssembler.toEntity(cart, food))
                .collect(Collectors.toList());

        // cart와 food가 위에서 만든 cartFood 객체와 연관관계를 맺을 수 있도록 해줌 //TODO 복잡한 연관관계 맺기 로직 없애 보기
        cartFoods.forEach((cartFood -> {
            cart.addCartFood(cartFood);
            foods.forEach((food)-> {
                food.addCartFood(cartFood);
            });
        }));

        cartFoodRepository.saveAll(cartFoods);

        return ResponseCustom.OK(CartResponse.toDto(cart));
    }
    @Transactional
    @Override
    public ResponseCustom<CartResponse> removeFoodsFromCart(
            Long cartIdx,
            RemoveFoodFromCartRequest request,
            Long userIdx
    )
    {
        Cart cart = cartRepository.findById(cartIdx).orElseThrow(CartNotFoundException::new);
        List<Food> removeFoods = foodRepository.findAllByFoodIdxIn(request.getRemoveFoodIdxes());
        List<CartFood> removeCartFoods = cartFoodRepository.findByCardIdxAndFoodIdxIn(cart.getCardIdx(), request.getRemoveFoodIdxes());

        removeCartFoods.forEach(cart::removeCartFood); //cart와의 연관관계 삭제
        removeCartFoods.forEach((rcf)->{ // food와의 연관관계 삭제
            removeFoods.forEach((rf)->rf.removeCartFood(rcf));
        });

        cartFoodRepository.deleteAll(removeCartFoods);

        return ResponseCustom.OK(CartResponse.toDto(cart));
    }
}