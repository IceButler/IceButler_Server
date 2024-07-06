package com.example.icebutler_server.cart.service;

import com.example.icebutler_server.cart.dto.request.AddFoodToCartRequest;
import com.example.icebutler_server.cart.dto.request.RemoveFoodFromCartRequest;
import com.example.icebutler_server.cart.dto.response.CartResponse;

import java.util.List;

public interface CartService {

    List<CartResponse> getCartFoods(Long fridgeIdx, Long userIdx);
    void addCartFoods(Long cartIdx, AddFoodToCartRequest request, Long userIdx);
    void deleteCartFoods(Long cartIdx, RemoveFoodFromCartRequest request, Long userIdx);
}
