package com.example.icebutler_server.cart.controller;

import com.example.icebutler_server.cart.dto.request.PostFoodCartRequest;
import com.example.icebutler_server.cart.service.CartService;
import com.example.icebutler_server.global.dto.response.ResponseCustom;

import com.example.icebutler_server.global.exception.BaseException;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Api(tags="1. cart API")
@RequestMapping("/cart")
@RestController
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;



    //장바구니 추가
    @GetMapping("/add/{owner}")
    public ResponseCustom<?> foodAdd(
            @PathVariable(name = "owner") Long owner,
            @RequestBody PostFoodCartRequest postFoodCartRequest) {
        return ResponseCustom.OK(cartService.foodAdd(owner,postFoodCartRequest));
    }

    // 장바구니 삭제
    @GetMapping("remove/{cartIdx}")
    public ResponseCustom<?> removeFridge(@PathVariable(name = "cartIdx") Long cartIdx) {
        return ResponseCustom.OK(cartService.foodDelete(cartIdx));
    }


//    @ApiOperation(value="장바구니 담기")
//    @ApiResponses({
//            @ApiResponse(code = 200, message = "OK", response = PostFoodCartResponse.class)
//    })
//    @PostMapping(value="/add")
//    public ResponseEntity<?> cartAdd(@RequestBody PostFoodCartRequest postFoodCartRequest) {
//        return ResponseEntity.ok(new BaseResponse(cartService.foodAdd(postFoodCartRequest)));
//    }

//    @ApiOperation(value="장바구니 삭제")
//    @ApiResponses({
//            @ApiResponse(code = 200, message = "OK", response = CancelFoodCartResponse.class)
//    })
//    @PostMapping(value="/delete")
//    public ResponseEntity<?> cartDelete(@RequestBody CancelFoodCartRequest cancelFoodCartRequest) {
//        return ResponseEntity.ok(new BaseResponse(cartService.foodDelete(cancelFoodCartRequest)));
//    }
}
