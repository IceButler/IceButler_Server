package com.example.icebutler_server.cart.controller;

import com.example.icebutler_server.cart.dto.cart.request.AddFoodToCartRequest;
import com.example.icebutler_server.cart.dto.cart.request.RemoveFoodFromCartRequest;
import com.example.icebutler_server.cart.service.MultiCartServiceImpl;
import com.example.icebutler_server.global.dto.response.ResponseCustom;
import com.example.icebutler_server.global.resolver.Auth;
import com.example.icebutler_server.global.resolver.IsLogin;
import com.example.icebutler_server.global.resolver.LoginStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/multiCarts")
@RestController
public class MultiCartController {

    private final MultiCartServiceImpl cartService;

    // 공용 장바구니 식품 조회
    @Auth
    @GetMapping("/{multiFridgeIdx}/foods")
    public ResponseCustom<?> getCartFood(@PathVariable Long multiFridgeIdx,
                                         @IsLogin LoginStatus loginStatus) {
        return cartService.getFoodsFromCart(multiFridgeIdx, loginStatus.getUserIdx());
    }

    // 공용 장바구니 식품 추가
    @Auth
    @PostMapping("/{multiFridgeIdx}/foods")
    public ResponseCustom<?> addFoodsToCart(@PathVariable Long multiFridgeIdx,
                                            @RequestBody AddFoodToCartRequest request,
                                            @IsLogin LoginStatus loginStatus) {
        return cartService.addFoodsToCart(multiFridgeIdx, request, loginStatus.getUserIdx());
    }

    // 공용 장바구니 식품 삭제
    @Auth
    @DeleteMapping("/{multiFridgeIdx}/foods")
    public ResponseCustom<?> removeCartFood(@PathVariable Long multiFridgeIdx,
                                                 @RequestBody RemoveFoodFromCartRequest request,
                                                 @IsLogin LoginStatus loginStatus) {
        return cartService.removeFoodsFromCart(multiFridgeIdx, request, loginStatus.getUserIdx());
    }

}
