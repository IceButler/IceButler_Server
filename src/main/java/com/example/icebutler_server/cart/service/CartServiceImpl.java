package com.example.icebutler_server.cart.service;

import com.example.icebutler_server.cart.dto.cart.assembler.CartFoodAssembler;
import com.example.icebutler_server.cart.dto.cart.request.AddFoodRequest;
import com.example.icebutler_server.cart.dto.cart.request.AddFoodToCartRequest;
import com.example.icebutler_server.cart.dto.cart.request.RemoveFoodFromCartRequest;
import com.example.icebutler_server.cart.dto.cart.response.CartResponse;
import com.example.icebutler_server.cart.entity.cart.Cart;
import com.example.icebutler_server.cart.entity.cart.CartFood;
import com.example.icebutler_server.cart.exception.CartNotFoundException;
import com.example.icebutler_server.cart.repository.cart.CartFoodRepository;
import com.example.icebutler_server.cart.repository.cart.CartRepository;
import com.example.icebutler_server.food.dto.assembler.FoodAssembler;
import com.example.icebutler_server.food.entity.Food;
import com.example.icebutler_server.food.entity.FoodCategory;
import com.example.icebutler_server.food.repository.FoodRepository;
import com.example.icebutler_server.fridge.entity.fridge.Fridge;
import com.example.icebutler_server.fridge.exception.FridgeNotFoundException;
import com.example.icebutler_server.fridge.exception.FridgeUserNotFoundException;
import com.example.icebutler_server.fridge.repository.fridge.FridgeRepository;
import com.example.icebutler_server.fridge.repository.fridge.FridgeUserRepository;
import com.example.icebutler_server.user.entity.User;
import com.example.icebutler_server.user.exception.UserNotFoundException;
import com.example.icebutler_server.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class CartServiceImpl implements CartService {

    private final UserRepository userRepository;
    private final CartFoodRepository cartFoodRepository;
    private final FoodRepository foodRepository;
    private final FridgeRepository fridgeRepository;
    private final CartFoodAssembler cartFoodAssembler;
    private final FridgeUserRepository fridgeUserRepository;
    private final FoodAssembler foodAssembler;
    private final CartRepository cartRepository;

    // 장바구니 식품 조회
    @Override
    public List<CartResponse> getCartFoods(Long fridgeIdx, Long userIdx) {
        Cart cart = getCart(userIdx, fridgeIdx);
        List<CartResponse> cartResponses = new ArrayList<>();
        for (FoodCategory category : FoodCategory.values()) {
            List<CartFood> cartFoods = cartFoodRepository.findByCartAndFood_FoodCategoryAndIsEnableOrderByCreatedAt(cart, category, true);
            // 카테고리별 음식이 있는 경우만 응답
            if(cartFoods.isEmpty()) continue;
            CartResponse cartResponse = CartResponse.toDto(cartFoods, category);
            cartResponses.add(cartResponse);
        }
        // 카테고리별 음식 개수 내림차순 정렬
        cartResponses.sort((cf1, cf2) -> cf2.getCartFoods().size() - cf1.getCartFoods().size());
        return cartResponses;
    }

    // 장바구니 식품 추가
    @Transactional
    @Override
    public void addCartFoods(Long fridgeIdx, AddFoodToCartRequest request, Long userIdx) {
        Cart cart = getCart(userIdx, fridgeIdx);
        // food 없는 경우 food 생성
        List<Food> foodRequests = new ArrayList<>();
        for(AddFoodRequest foodRequest : request.getFoodRequests()) {
            Food food = this.foodRepository.findByFoodNameAndFoodCategory(foodRequest.getFoodName(), FoodCategory.getFoodCategoryByName(foodRequest.getFoodCategory()));
            if(food == null) food = this.foodRepository.save(foodAssembler.toEntity(foodRequest));
            foodRequests.add(food);
        }

        // 장바구니 내 식품 유무 확인
        List<Long> foodsInNowCart = this.cartFoodRepository.findByCartAndIsEnable(cart, true).stream()
                .map((cf) -> cf.getFood().getFoodIdx())
                .collect(Collectors.toList());
        List<CartFood> cartFoods = foodRequests.stream()
                .filter((f) -> {
                    for (Long foodInIdx : foodsInNowCart) if(foodInIdx.equals(f.getFoodIdx())) return false;
                    return true;
                })
                .map((food) -> cartFoodAssembler.toEntity(cart, food))
                .collect(Collectors.toList());

        cartFoodRepository.saveAll(cartFoods);
    }

    // 장바구니 식품 삭제
    @Transactional
    @Override
    public void deleteCartFoods(Long fridgeIdx, RemoveFoodFromCartRequest request, Long userIdx) {
        Cart cart = getCart(userIdx, fridgeIdx);
        List<CartFood> removeCartFoods = cartFoodRepository.findByCartIdxAndFoodIdxIn(cart.getCartIdx(), request.getFoodIdxes());
        cartFoodRepository.deleteAll(removeCartFoods);
    }

    private Cart getCart(Long userIdx, Long fridgeIdx) {
        User user = userRepository.findByUserIdxAndIsEnable(userIdx, true).orElseThrow(UserNotFoundException::new);
        Fridge fridge = fridgeRepository.findByFridgeIdxAndIsEnable(fridgeIdx, true).orElseThrow(FridgeNotFoundException::new);
        fridgeUserRepository.findByUserAndFridgeAndIsEnable(user, fridge, true).orElseThrow(FridgeUserNotFoundException::new);
        return cartRepository.findByFridge_FridgeIdxAndIsEnable(fridgeIdx, true).orElseThrow(CartNotFoundException::new);
    }
}
