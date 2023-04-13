package com.example.icebutler_server.cart.service;

import com.example.icebutler_server.cart.dto.cart.assembler.MultiCartFoodAssembler;
import com.example.icebutler_server.cart.dto.cart.request.AddFoodRequest;
import com.example.icebutler_server.cart.dto.cart.request.AddFoodToCartRequest;
import com.example.icebutler_server.cart.dto.cart.request.RemoveFoodFromCartRequest;
import com.example.icebutler_server.cart.dto.cart.response.CartResponse;
import com.example.icebutler_server.cart.entity.multiCart.MultiCart;
import com.example.icebutler_server.cart.entity.multiCart.MultiCartFood;
import com.example.icebutler_server.cart.exception.CartNotFoundException;
import com.example.icebutler_server.cart.repository.multiCart.MultiCartFoodRepository;
import com.example.icebutler_server.cart.repository.multiCart.MultiCartRepository;
import com.example.icebutler_server.food.entity.FoodCategory;
import com.example.icebutler_server.food.entity.Food;
import com.example.icebutler_server.food.repository.FoodRepository;
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
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class MultiCartServiceImpl implements CartService {

    private final UserRepository userRepository;
    private final MultiFridgeRepository multiFridgeRepository;
    private final MultiFridgeUserRepository multiFridgeUserRepository;
    private final MultiCartFoodRepository multiCartFoodRepository;
    private final MultiCartRepository multiCartRepository;
    private final FoodRepository foodRepository;
    private final MultiCartFoodAssembler multiCartFoodAssembler;

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

    @Transactional
    @Override
    public ResponseCustom<?> addFoodsToCart(Long fridgeIdx, AddFoodToCartRequest request, Long userIdx) {
        User user = this.userRepository.findByUserIdxAndIsEnable(userIdx, true).orElseThrow(UserNotFoundException::new);
        MultiFridge fridge = multiFridgeRepository.findByMultiFridgeIdxAndIsEnable(fridgeIdx, true).orElseThrow(FridgeNotFoundException::new);
        MultiFridgeUser fridgeUser = multiFridgeUserRepository.findByMultiFridgeAndUserAndIsEnable(fridge, user, true).orElseThrow(FridgeUserNotFoundException::new);
        MultiCart cart = multiCartRepository.findByMultiFridgeUserAndIsEnable(fridgeUser, true).orElseThrow(CartNotFoundException::new);

        // food 없는 경우 food 생성
        List<Food> foodRequests = new ArrayList<>();
        for(AddFoodRequest foodRequest : request.getFoodRequests()) {
            Food food = this.foodRepository.findByFoodNameAndFoodCategory(foodRequest.getFoodName(), FoodCategory.getFoodCategoryByName(foodRequest.getFoodCategory()));
            if(food == null) food = this.foodRepository.save(new Food(foodRequest.getFoodName(), FoodCategory.getFoodCategoryByName(foodRequest.getFoodCategory())));
            foodRequests.add(food);
        }

        // 장바구니 내 식품 유무 확인
        List<Long> foodsInNowCart = this.multiCartFoodRepository.findByMultiCartAndIsEnable(cart, true).stream()
                .map((cf) -> cf.getFood().getFoodIdx()).collect(Collectors.toList());
        List<MultiCartFood> cartFoods = foodRequests.stream()
                .filter((f) -> {
                    for (Long foodInIdx : foodsInNowCart) if(foodInIdx.equals(f.getFoodIdx())) return false;
                    return true;
                })
                .map((food) -> multiCartFoodAssembler.toEntity(cart, food))
                .collect(Collectors.toList());

        multiCartFoodRepository.saveAll(cartFoods);
        return ResponseCustom.OK();
    }

    @Transactional
    @Override
    public ResponseCustom<?> removeFoodsFromCart(Long fridgeIdx, RemoveFoodFromCartRequest request, Long userIdx) {
        User user = this.userRepository.findByUserIdxAndIsEnable(userIdx, true).orElseThrow(UserNotFoundException::new);
        MultiFridge fridge = multiFridgeRepository.findByMultiFridgeIdxAndIsEnable(fridgeIdx, true).orElseThrow(FridgeNotFoundException::new);
        MultiFridgeUser fridgeUser = multiFridgeUserRepository.findByMultiFridgeAndUserAndIsEnable(fridge, user, true).orElseThrow(FridgeUserNotFoundException::new);
        MultiCart cart = multiCartRepository.findByMultiFridgeUserAndIsEnable(fridgeUser, true).orElseThrow(CartNotFoundException::new);
        List<MultiCartFood> removeCartFoods = multiCartFoodRepository.findByCartIdxAndFoodIdxInAndIsEnable(cart.getMultiCartIdx(), request.getFoodIdxes(), true);

        multiCartFoodRepository.deleteAll(removeCartFoods);
        return ResponseCustom.OK();
    }
}
