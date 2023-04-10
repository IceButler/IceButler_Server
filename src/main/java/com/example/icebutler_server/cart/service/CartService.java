package com.example.icebutler_server.cart.service;

import com.example.icebutler_server.cart.dto.cart.request.AddFoodToCartRequest;
import com.example.icebutler_server.cart.dto.cart.request.RemoveFoodFromCartRequest;
import com.example.icebutler_server.cart.dto.cart.response.CartResponse;
import com.example.icebutler_server.global.dto.response.ResponseCustom;

public interface CartService {

    ResponseCustom<?> getFoodsFromCart(Long fridgeIdx, Long userIdx);
    ResponseCustom<?> addFoodsToCart(Long cartIdx, AddFoodToCartRequest request, Long userIdx);
    ResponseCustom<?> removeFoodsFromCart(Long cartIdx, RemoveFoodFromCartRequest request, Long userIdx);
}
