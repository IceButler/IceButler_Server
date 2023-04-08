package com.example.icebutler_server.cart.service;

import com.example.icebutler_server.cart.dto.cart.assembler.CartAssembler;
import com.example.icebutler_server.cart.dto.cart.assembler.CartFoodAssembler;
import com.example.icebutler_server.cart.dto.cart.request.AddFoodToCartRequest;
import com.example.icebutler_server.cart.dto.cart.request.RemoveFoodFromCartRequest;
import com.example.icebutler_server.cart.dto.cart.response.CartResponse;
import com.example.icebutler_server.cart.entity.cart.Cart;
import com.example.icebutler_server.cart.entity.cart.CartFood;
import com.example.icebutler_server.cart.repository.cart.CartFoodRepository;
import com.example.icebutler_server.cart.repository.cart.CartRepository;
import com.example.icebutler_server.food.entity.Food;
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

import java.util.List;
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
    public ResponseCustom<CartResponse> getFoodsFromCart(Long fridgeIdx, Long userIdx) {
        User user = userRepository.findByUserIdxAndIsEnable(userIdx, true).orElseThrow(UserNotFoundException::new);
        Fridge fridge = fridgeRepository.findByFridgeIdxAndIsEnable(fridgeIdx, true).orElseThrow(FridgeNotFoundException::new);
        fridgeUserRepository.findByUserAndFridge(user, fridge).orElseThrow(FridgeUserNotFoundException::new);

        List<CartFood> cartFood = cartFoodRepository.findByCart(fridge.getCart());
        return ResponseCustom.OK(CartResponse.toDto(cartFood));
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

        List<Food> foods = foodRepository.findAllByFoodIdxIn(request.getFoodIdxes());

        List<Long> foodsInNowCart = cart.getCartFoods().stream()
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
