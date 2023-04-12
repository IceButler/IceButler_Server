package com.example.icebutler_server.cart.service;

import com.example.icebutler_server.cart.dto.cart.assembler.CartAssembler;
import com.example.icebutler_server.cart.dto.cart.assembler.CartFoodAssembler;
import com.example.icebutler_server.cart.dto.cart.request.AddFoodRequest;
import com.example.icebutler_server.cart.dto.cart.request.AddFoodToCartRequest;
import com.example.icebutler_server.cart.dto.cart.request.RemoveFoodFromCartRequest;
import com.example.icebutler_server.cart.dto.cart.response.CartResponse;
import com.example.icebutler_server.cart.entity.cart.Cart;
import com.example.icebutler_server.cart.entity.cart.CartFood;
import com.example.icebutler_server.cart.repository.cart.CartFoodRepository;
import com.example.icebutler_server.cart.repository.cart.CartRepository;
import com.example.icebutler_server.food.dto.response.FoodResponse;
import com.example.icebutler_server.food.entity.Food;
import com.example.icebutler_server.food.entity.FoodCategory;
import com.example.icebutler_server.food.repository.FoodRepository;
import com.example.icebutler_server.fridge.entity.fridge.Fridge;
import com.example.icebutler_server.fridge.exception.FridgeNotFoundException;
import com.example.icebutler_server.fridge.exception.FridgeUserNotFoundException;
import com.example.icebutler_server.fridge.repository.fridge.FridgeRepository;
import com.example.icebutler_server.fridge.repository.fridge.FridgeUserRepository;
import com.example.icebutler_server.global.dto.response.ResponseCustom;
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
    private final CartRepository cartRepository;
    private final FoodRepository foodRepository;
    private final FridgeRepository fridgeRepository;
    private final CartAssembler cartAssembler;
    private final CartFoodAssembler cartFoodAssembler;
    private final FridgeUserRepository fridgeUserRepository;


    @Override
    public ResponseCustom<?> getFoodsFromCart(Long fridgeIdx, Long userIdx) {
        User user = userRepository.findByUserIdxAndIsEnable(userIdx, true).orElseThrow(UserNotFoundException::new);
        Fridge fridge = fridgeRepository.findByFridgeIdxAndIsEnable(fridgeIdx, true).orElseThrow(FridgeNotFoundException::new);
        fridgeUserRepository.findByUserAndFridge(user, fridge).orElseThrow(FridgeUserNotFoundException::new);

        List<CartResponse> cartResponses = new ArrayList<>();
        for (FoodCategory category : FoodCategory.values()) {
            List<CartFood> cartFoods = cartFoodRepository.findByCartAndFood_FoodCategoryAndIsEnableOrderByCreatedAt(fridge.getCart(), category, true);
            // 카테고리별 음식이 있는 경우만 응답
            if(cartFoods.isEmpty()) continue;
            CartResponse cartResponse = CartResponse.doDto(cartFoods, category);
            cartResponses.add(cartResponse);
        }
        // 카테고리별 음식 개수 내림차순 정렬
        cartResponses.sort((cf1, cf2) -> cf2.getCartFoods().size() - cf1.getCartFoods().size());
        return ResponseCustom.OK(cartResponses);
    }

    @Transactional
    @Override
    public ResponseCustom<?> addFoodsToCart(
            Long fridgeIdx,
            AddFoodToCartRequest request,
            Long userIdx
    )
    {
        User user = userRepository.findByUserIdxAndIsEnable(userIdx, true).orElseThrow(UserNotFoundException::new);
        Fridge fridge = fridgeRepository.findByFridgeIdxAndIsEnable(fridgeIdx, true).orElseThrow(FridgeNotFoundException::new);
        fridgeUserRepository.findByUserAndFridge(user, fridge).orElseThrow(FridgeUserNotFoundException::new);
        Cart cart = fridge.getCart();
        List<Food> foods = new ArrayList<>();

        // foodName과 category로 찾아서 있으면 foodIdx, 없으면 food에 저장 후 foodIdx
        for(AddFoodRequest foodRequest : request.getFoodrequests()) {
            Food food;
            food = foodRepository.findByFoodNameAndFoodCategory(foodRequest.getFoodName(), FoodCategory.getFoodCategoryByName(foodRequest.getFoodCategory()));
            if(food==null) {
                foodRepository.save(new Food(foodRequest.getFoodName(), FoodCategory.getFoodCategoryByName(foodRequest.getFoodCategory())));
                food = foodRepository.findByFoodNameAndFoodCategory(foodRequest.getFoodName(), FoodCategory.getFoodCategoryByName(foodRequest.getFoodCategory()));
            }
            foods.add(food);
        }

//        List<Food> foods = foodRepository.findAllByFoodIdxIn(request.getFoodIdxes());
//
        List<Long> foodsInNowCart = this.cartFoodRepository.findByCartAndIsEnable(cart, true).stream()
                        .map((cf) -> cf.getFood().getFoodIdx()).collect(Collectors.toList());

        List<CartFood> cartFoods = foods.stream()
                .filter((f)->{
                    for (Long foodInIdx : foodsInNowCart) if(foodInIdx.equals(f.getFoodIdx())) return false;
                    return true;
                })
                .map((food) -> cartFoodAssembler.toEntity(cart, food))
                .collect(Collectors.toList());

        cartFoodRepository.saveAll(cartFoods);

        return ResponseCustom.OK();
    }
    @Transactional
    @Override
    public ResponseCustom<?> removeFoodsFromCart(
            Long fridgeIdx,
            RemoveFoodFromCartRequest request,
            Long userIdx
    )
    {
        User user = userRepository.findByUserIdxAndIsEnable(userIdx, true).orElseThrow(UserNotFoundException::new);
        Fridge fridge = fridgeRepository.findByFridgeIdxAndIsEnable(fridgeIdx, true).orElseThrow(FridgeNotFoundException::new);
        fridgeUserRepository.findByUserAndFridge(user, fridge).orElseThrow(FridgeUserNotFoundException::new);
        Cart cart = fridge.getCart();

        List<CartFood> removeCartFoods = cartFoodRepository.findByCartIdxAndFoodIdxIn(cart.getCartIdx(), request.getFoodIdxes());
        cartFoodRepository.deleteAll(removeCartFoods);

        return ResponseCustom.OK();
    }
}
