package com.example.icebutler_server.cart.service;

import com.example.icebutler_server.cart.dto.cart.request.AddFoodToCartRequest;
import com.example.icebutler_server.cart.dto.cart.request.RemoveFoodFromCartRequest;
import com.example.icebutler_server.cart.dto.cart.response.CartResponse;
import com.example.icebutler_server.global.dto.response.ResponseCustom;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class MultiCartServiceImpl implements CartService {

    @Override
    public ResponseCustom<CartResponse> getFoodsFromCart(Long cartIdx, Long userIdx) {
        return null;
    }

    @Override
    public ResponseCustom<CartResponse> addFoodsToCart(Long cartIdx, AddFoodToCartRequest request, Long userIdx) {
        return null;
    }

    @Override
    public ResponseCustom<CartResponse> removeFoodsFromCart(Long cartIdx, RemoveFoodFromCartRequest request, Long userIdx) {
        return null;
    }
}
