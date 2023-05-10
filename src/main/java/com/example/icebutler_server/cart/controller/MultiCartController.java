package com.example.icebutler_server.cart.controller;

import com.example.icebutler_server.cart.dto.cart.request.AddFoodToCartRequest;
import com.example.icebutler_server.cart.dto.cart.request.RemoveFoodFromCartRequest;
import com.example.icebutler_server.cart.dto.cart.response.CartResponse;
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

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/multiCarts")
@RestController
public class MultiCartController {

    private final MultiCartServiceImpl cartService;

    // 공용 장바구니 식품 조회
    @Auth
    @GetMapping("/{multiFridgeIdx}/foods")
    public ResponseCustom<List<CartResponse>> getCartFoods(@PathVariable Long multiFridgeIdx,
                                                          @IsLogin LoginStatus loginStatus) {
        return ResponseCustom.OK(cartService.getCartFoods(multiFridgeIdx, loginStatus.getUserIdx()));
    }

    // 공용 장바구니 식품 추가
    @Auth
    @PostMapping("/{multiFridgeIdx}/foods")
    public ResponseCustom<?> addCartFoods(@PathVariable Long multiFridgeIdx,
                                          @RequestBody AddFoodToCartRequest request,
                                          @IsLogin LoginStatus loginStatus) {
        cartService.addCartFoods(multiFridgeIdx, request, loginStatus.getUserIdx());
        return ResponseCustom.OK();
    }

    // 공용 장바구니 식품 삭제
    @Auth
    @DeleteMapping("/{multiFridgeIdx}/foods")
    public ResponseCustom<?> deleteCartFoods(@PathVariable Long multiFridgeIdx,
                                             @RequestBody RemoveFoodFromCartRequest request,
                                             @IsLogin LoginStatus loginStatus) {
        cartService.deleteCartFoods(multiFridgeIdx, request, loginStatus.getUserIdx());
        return ResponseCustom.OK();
    }

}
