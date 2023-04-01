package com.example.icebutler_server.fridge.controller;

import com.example.icebutler_server.fridge.dto.request.AddFoodToCartRequest;
import com.example.icebutler_server.fridge.service.CartService;
import com.example.icebutler_server.global.dto.response.ResponseCustom;
import com.example.icebutler_server.global.resolver.Auth;
import com.example.icebutler_server.global.resolver.IsLogin;
import com.example.icebutler_server.global.resolver.LoginStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("carts")
@RestController
public class CartController {

    private final CartService cartService;

    @Auth
    @PostMapping("/{cartId}/foods")
    public ResponseCustom<Void> addFoods(
            @PathVariable Long cartId,
            @RequestBody AddFoodToCartRequest request,
            @IsLogin LoginStatus loginStatus
    )
    {
        return cartService.addFoodsToCart(cartId, request, loginStatus.getUserIdx());
    }

}
