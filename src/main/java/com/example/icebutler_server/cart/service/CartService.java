package com.example.icebutler_server.cart.service;

import com.example.icebutler_server.cart.dto.request.CancelFoodCartRequest;
import com.example.icebutler_server.cart.dto.request.PostFoodCartRequest;
import com.example.icebutler_server.cart.dto.response.CancelFoodCartResponse;
import com.example.icebutler_server.cart.dto.response.PostFoodCartResponse;
import com.example.icebutler_server.cart.entity.Cart;
import com.example.icebutler_server.cart.repository.CartRepository;
import com.example.icebutler_server.food.entity.Food;
import com.example.icebutler_server.food.entity.repository.FoodRepository;
import com.example.icebutler_server.user.entity.User;
import com.example.icebutler_server.user.entity.userRepository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
//@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CartService {
    private final UserRepository userRepository;
    private final FoodRepository foodRepository;
    private final CartRepository cartRepository;

    @Transactional
    public PostFoodCartResponse foodAdd(PostFoodCartRequest postFoodCartRequest) {
        User user = userRepository.findById(postFoodCartRequest.getUserIdx())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        Cart cart = user.getCart();

        if (cart == null) {
            cart= Cart.builder()
                    .owner(user)
                    .build();
        }

        Food food = foodRepository.findById(postFoodCartRequest.getFoodIdx())
                .orElseThrow(() -> new EntityNotFoundException("Food not found"));

        food = Food.builder()
                .foodIdx(food.getFoodIdx())
                .foodCategory(food.getFoodCategory())
                .foodName(food.getFoodName())
                .foodIconName(food.getFoodIconName())
                .owner(food.getOwner())
                .fridgeFood(food.getFridgeFood())
                .status(food.isStatus())
                .cart(cart) // cart 필드 설정
                .build();
//        food.setCart(cart);
        cart.getFoods().add(food);

        cartRepository.save(cart);
        foodRepository.save(food);

        PostFoodCartResponse postFoodCartResponse = PostFoodCartResponse.builder()
                .cartIdx(cart.getCardIdx())
                .foodIdx(food.getFoodIdx())
                .foodName(food.getFoodName())
                .message("음식을 장바구니에 담았습니다.")
                .build();

        return postFoodCartResponse;
    }

    @Transactional
    public CancelFoodCartResponse foodDelete(CancelFoodCartRequest cancelFoodCartRequest){
        User user = userRepository.findById(cancelFoodCartRequest.getUserIdx())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        Cart cart = user.getCart();

        if (cart == null) {
            throw new EntityNotFoundException("Cart not found");
        }

        Food food = foodRepository.findById(cancelFoodCartRequest.getFoodIdx())
                .orElseThrow(() -> new EntityNotFoundException("Food not found"));

        if (!cart.getFoods().contains(food)) {
            throw new EntityNotFoundException("Food not found in the cart");
        }
        cart.getFoods().remove(food);

        cartRepository.save(cart);

        CancelFoodCartResponse deleteFoodCartResponse = CancelFoodCartResponse.builder()
                .cartIdx(cart.getCardIdx())
                .foodIdx(food.getFoodIdx())
                .foodName(food.getFoodName())
                .message("음식을 장바구니에 삭제했습니다")
                .build();

        return deleteFoodCartResponse;
    }
}
