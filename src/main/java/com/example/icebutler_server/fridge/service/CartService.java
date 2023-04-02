package com.example.icebutler_server.fridge.service;

import com.example.icebutler_server.fridge.dto.request.AddFoodToCartRequest;
import com.example.icebutler_server.fridge.dto.request.RemoveFoodFromCartRequest;
import com.example.icebutler_server.fridge.dto.response.CartResponse;
import com.example.icebutler_server.global.dto.response.ResponseCustom;

public interface CartService {

    ResponseCustom<CartResponse> getFoodsFromCart(Long cartIdx, Long userIdx);
    ResponseCustom<Void> addFoodsToCart(Long cartIdx, AddFoodToCartRequest request, Long userIdx);
    ResponseCustom<Void> removeFoodsFromCart(Long cartIdx, RemoveFoodFromCartRequest request, Long userIdx);
}
