package com.example.icebutler_server.cart.controller;

import com.example.icebutler_server.cart.dto.request.CancelFoodCartRequest;
import com.example.icebutler_server.cart.dto.request.PostFoodCartRequest;
import com.example.icebutler_server.cart.dto.response.CancelFoodCartResponse;
import com.example.icebutler_server.cart.dto.response.PostFoodCartResponse;
import com.example.icebutler_server.cart.service.CartService;
import com.example.icebutler_server.global.BaseResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags="1. cart API")
@RequestMapping("/cart")
@RestController
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;



    @ApiOperation(value="장바구니 담기")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK", response = PostFoodCartResponse.class)
    })
    @PostMapping(value="/add")
    public ResponseEntity<?> cartAdd(@RequestBody PostFoodCartRequest postFoodCartRequest) {
        return ResponseEntity.ok(new BaseResponse(cartService.foodAdd(postFoodCartRequest)));
    }

    @ApiOperation(value="장바구니 삭제")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK", response = CancelFoodCartResponse.class)
    })
    @PostMapping(value="/delete")
    public ResponseEntity<?> cartDelete(@RequestBody CancelFoodCartRequest cancelFoodCartRequest) {
        return ResponseEntity.ok(new BaseResponse(cartService.foodDelete(cancelFoodCartRequest)));
    }
}
