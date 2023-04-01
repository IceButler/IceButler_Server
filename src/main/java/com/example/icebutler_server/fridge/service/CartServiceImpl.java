package com.example.icebutler_server.fridge.service;

import com.example.icebutler_server.fridge.dto.assembler.CartFoodAssembler;
import com.example.icebutler_server.fridge.dto.request.AddFoodToCartRequest;
import com.example.icebutler_server.fridge.dto.request.DeleteFoodFromCartRequest;
import com.example.icebutler_server.fridge.entity.Cart;
import com.example.icebutler_server.fridge.entity.CartFood;
import com.example.icebutler_server.fridge.entity.Food;
import com.example.icebutler_server.fridge.exception.CartNotFoundException;
import com.example.icebutler_server.fridge.repository.CartRepository;
import com.example.icebutler_server.fridge.repository.FoodRepository;
import com.example.icebutler_server.global.dto.response.ResponseCustom;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class CartServiceImpl implements CartService{
    private final CartRepository cartRepository;
    private final FoodRepository foodRepository;
    private final CartFoodAssembler cartFoodAssembler;

    @Transactional
    @Override
    public ResponseCustom<Void> addFoodsToCart(
            Long cartId,
            AddFoodToCartRequest request,
            Long userIdx
    )
    {
        Cart cart = cartRepository.findById(cartId).orElseThrow(CartNotFoundException::new);
        List<Food> foods = foodRepository.findAllByFoodIdxIn(request.getAddFoodIdxes());
        // cart와 food의 연관관계를 맺어주는 cartFood 객체 생성
        List<CartFood> cartFoods = foods.stream()
                .map((food) -> cartFoodAssembler.toEntity(cart, food))
                .collect(Collectors.toList());
        // cart와 food가 위에서 만든 cartFood 객체와 연관관계를 맺을 수 있도록 해줌 //TODO 복잡한 연관관계 맺기 로직 없애 보기
        cartFoods.forEach((cartFood -> {
            cart.addCartFood(cartFood);
            foods.forEach((food)-> {
                food.addCartFood(cartFood);
            });
        }));
        return ResponseCustom.OK();
    }
}