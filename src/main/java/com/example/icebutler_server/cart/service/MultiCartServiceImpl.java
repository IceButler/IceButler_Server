package com.example.icebutler_server.cart.service;

import com.example.icebutler_server.cart.dto.cart.request.AddFoodToCartRequest;
import com.example.icebutler_server.cart.dto.cart.request.RemoveFoodFromCartRequest;
import com.example.icebutler_server.cart.dto.cart.response.CartResponse;
import com.example.icebutler_server.cart.entity.multiCart.MultiCart;
import com.example.icebutler_server.cart.entity.multiCart.MultiCartFood;
import com.example.icebutler_server.cart.exception.CartNotFoundException;
import com.example.icebutler_server.cart.repository.multiCart.MultiCartFoodRepository;
import com.example.icebutler_server.cart.repository.multiCart.MultiCartRepository;
import com.example.icebutler_server.food.entity.FoodCategory;
import com.example.icebutler_server.fridge.entity.multiFridge.MultiFridge;
import com.example.icebutler_server.fridge.entity.multiFridge.MultiFridgeUser;
import com.example.icebutler_server.fridge.exception.FridgeNotFoundException;
import com.example.icebutler_server.fridge.exception.FridgeUserNotFoundException;
import com.example.icebutler_server.fridge.repository.multiFridge.MultiFridgeRepository;
import com.example.icebutler_server.fridge.repository.multiFridge.MultiFridgeUserRepository;
import com.example.icebutler_server.global.dto.response.ResponseCustom;
import com.example.icebutler_server.user.entity.User;
import com.example.icebutler_server.user.exception.UserNotFoundException;
import com.example.icebutler_server.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class MultiCartServiceImpl implements CartService {

    private final UserRepository userRepository;
    private final MultiFridgeRepository multiFridgeRepository;
    private final MultiFridgeUserRepository multiFridgeUserRepository;
    private final MultiCartFoodRepository multiCartFoodRepository;
    private final MultiCartRepository multiCartRepository;

    @Override
    public ResponseCustom<?> getFoodsFromCart(Long fridgeIdx, Long userIdx) {
        User user = userRepository.findByUserIdxAndIsEnable(userIdx, true).orElseThrow(UserNotFoundException::new);
        MultiFridge fridge = multiFridgeRepository.findByMultiFridgeIdxAndIsEnable(fridgeIdx, true).orElseThrow(FridgeNotFoundException::new);
        MultiFridgeUser fridgeUser = multiFridgeUserRepository.findByMultiFridgeAndUserAndIsEnable(fridge, user, true).orElseThrow(FridgeUserNotFoundException::new);
        MultiCart cart = multiCartRepository.findByMultiFridgeUserAndIsEnable(fridgeUser, true).orElseThrow(CartNotFoundException::new);

        List<CartResponse> cartResponses = new ArrayList<>();
        for (FoodCategory category : FoodCategory.values()) {
            List<MultiCartFood> cartFoods = multiCartFoodRepository.findByMultiCartAndFood_FoodCategoryAndIsEnableOrderByCreatedAt(cart, category, true);
            // 카테고리별 음식이 있는 경우만 응답
            if(cartFoods.isEmpty()) continue;
            CartResponse cartResponse = CartResponse.doMultiDto(cartFoods, category);
            cartResponses.add(cartResponse);
        }
        // 카테고리별 음식 개수 내림차순 정렬
        cartResponses.sort((cf1, cf2) -> cf2.getCartFoods().size() - cf1.getCartFoods().size());
        return ResponseCustom.OK(cartResponses);
    }

    @Override
    public ResponseCustom<?> addFoodsToCart(Long cartIdx, AddFoodToCartRequest request, Long userIdx) {
        return null;
    }

    @Override
    public ResponseCustom<CartResponse> removeFoodsFromCart(Long cartIdx, RemoveFoodFromCartRequest request, Long userIdx) {
        return null;
    }
}
