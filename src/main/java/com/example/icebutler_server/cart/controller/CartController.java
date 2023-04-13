package com.example.icebutler_server.cart.controller;

import com.example.icebutler_server.cart.dto.cart.request.AddFoodToCartRequest;
import com.example.icebutler_server.cart.dto.cart.request.RemoveFoodFromCartRequest;
import com.example.icebutler_server.cart.dto.cart.response.CartResponse;
import com.example.icebutler_server.cart.service.CartServiceImpl;
import com.example.icebutler_server.global.dto.response.ResponseCustom;
import com.example.icebutler_server.global.resolver.Auth;
import com.example.icebutler_server.global.resolver.IsLogin;
import com.example.icebutler_server.global.resolver.LoginStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/carts")
@RestController
public class CartController {

    private final CartServiceImpl cartService;

    // 장바구니 식품 조회
    @Auth
    @GetMapping("/{fridgeIdx}/foods")
    public ResponseCustom<?> getFoodsFromCart(
            @PathVariable Long fridgeIdx,
            @IsLogin LoginStatus loginStatus
    )
    {
        return cartService.getFoodsFromCart(fridgeIdx, loginStatus.getUserIdx());
    }

    // 장바구니 식품 추가
    @Auth
    @PostMapping("/{fridgeIdx}/foods")
    public ResponseCustom<?> addFoodsToCart(
            @PathVariable Long fridgeIdx,
            @RequestBody AddFoodToCartRequest request,
            @IsLogin LoginStatus loginStatus
    )
    {
        return cartService.addFoodsToCart(fridgeIdx, request, loginStatus.getUserIdx());
    }

    // 장바구니 식품 삭제
    @Auth
    @PutMapping("/{fridgeIdx}/foods")
    public ResponseCustom<?> removeFoodsFromCart(
            @PathVariable Long fridgeIdx,
            @RequestBody RemoveFoodFromCartRequest request,
            @IsLogin LoginStatus loginStatus
    )
    {
        return cartService.removeFoodsFromCart(fridgeIdx, request, loginStatus.getUserIdx());
    }
}
