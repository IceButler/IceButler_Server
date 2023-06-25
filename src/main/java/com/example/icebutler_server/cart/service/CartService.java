package com.example.icebutler_server.cart.service;

import com.example.icebutler_server.cart.dto.cart.request.AddFoodToCartRequest;
import com.example.icebutler_server.cart.dto.cart.request.RemoveFoodFromCartRequest;
import com.example.icebutler_server.cart.dto.cart.response.CartResponse;

import java.util.List;

public interface CartService {

    List<CartResponse> getCartFoods(Long fridgeIdx, Long userIdx);
    boolean addCartFoods(Long cartIdx, AddFoodToCartRequest request, Long userIdx);
    void deleteCartFoods(Long cartIdx, RemoveFoodFromCartRequest request, Long userIdx);
}
