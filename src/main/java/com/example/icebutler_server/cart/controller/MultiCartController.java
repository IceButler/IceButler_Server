package com.example.icebutler_server.cart.controller;

import com.example.icebutler_server.cart.service.MultiCartServiceImpl;
import com.example.icebutler_server.global.dto.response.ResponseCustom;
import com.example.icebutler_server.global.resolver.IsLogin;
import com.example.icebutler_server.global.resolver.LoginStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/multiCarts")
@RestController
public class MultiCartController {

    private final MultiCartServiceImpl cartService;

    // 공용 장바구니 식품 조회
    @GetMapping("/{multiFridgeIdx}/foods")
    public ResponseCustom<?> getCartFood( @PathVariable Long multiFridgeIdx,
                                          @IsLogin LoginStatus loginStatus) {
        return cartService.getFoodsFromCart(multiFridgeIdx, loginStatus.getUserIdx());
    }
}
