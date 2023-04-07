package com.example.icebutler_server.cart.controller;

import com.example.icebutler_server.cart.dto.cart.response.CartResponse;
import com.example.icebutler_server.cart.service.CartServiceImpl;
import com.example.icebutler_server.global.dto.response.ResponseCustom;
import com.example.icebutler_server.global.resolver.Auth;
import com.example.icebutler_server.global.resolver.IsLogin;
import com.example.icebutler_server.global.resolver.LoginStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/carts")
@RestController
public class CartController {

    private final CartServiceImpl cartService;

    // 장바구니 식품 조회
    @Auth
    @GetMapping("/{fridgeIdx}/foods")
    public ResponseCustom<CartResponse> getFoodsFromCart(
            @PathVariable Long fridgeIdx,
            @IsLogin LoginStatus loginStatus
    )
    {
        return cartService.getFoodsFromCart(fridgeIdx, loginStatus.getUserIdx());
    }
//
//    @Auth
//    @PostMapping("/{cartId}/foods")
//    public ResponseCustom<CartResponse> addFoodsToCart(
//            @PathVariable Long cartId,
//            @RequestBody AddFoodToCartRequest request,
//            @IsLogin LoginStatus loginStatus
//    )
//    {
//        return cartService.addFoodsToCart(cartId, request, loginStatus.getUserIdx());
//    }
//
//    @Auth
//    @PutMapping("/{cartId}/foods")
//    public ResponseCustom<CartResponse> removeFoodsFromCart(
//            @PathVariable Long cartId,
//            @RequestBody RemoveFoodFromCartRequest request,
//            @IsLogin LoginStatus loginStatus
//    )
//    {
//        return cartService.removeFoodsFromCart(cartId, request, loginStatus.getUserIdx());
//    }
}
