package com.example.icebutler_server.cart.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/carts")
@RestController
public class CartController {

//    private final CartService cartService;
//
//    @Auth
//    @GetMapping("/{cartId}/foods")
//    public ResponseCustom<CartResponse> getFoodsFromCart(
//            @PathVariable Long cartId,
//            @IsLogin LoginStatus loginStatus
//    )
//    {
//        return cartService.getFoodsFromCart(cartId, loginStatus.getUserIdx());
//    }
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
