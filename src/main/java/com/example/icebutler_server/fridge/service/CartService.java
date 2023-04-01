package com.example.icebutler_server.fridge.service;

import com.example.icebutler_server.fridge.dto.request.AddFoodToCartRequest;
import com.example.icebutler_server.fridge.dto.request.DeleteFoodFromCartRequest;
import com.example.icebutler_server.global.dto.response.ResponseCustom;

public interface CartService {
    ResponseCustom<Void> addFoodsToCart(Long cartId, AddFoodToCartRequest request, Long userIdx);
}
