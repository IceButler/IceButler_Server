package com.example.icebutler_server.cart.service;

import com.example.icebutler_server.cart.dto.request.AddFoodToCartRequest;
import com.example.icebutler_server.cart.dto.request.RemoveFoodFromCartRequest;
import com.example.icebutler_server.cart.dto.response.CartResponse;

import java.util.List;

public interface CartService {

    List<CartResponse> getCartFoods(Long fridgeId, Long userId);
    void addCartFoods(Long cartId, AddFoodToCartRequest request, Long userId);
    void deleteCartFoods(Long cartId, RemoveFoodFromCartRequest request, Long userId);
}
