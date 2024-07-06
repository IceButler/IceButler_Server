package com.example.icebutler_server.cart.controller;

import com.example.icebutler_server.cart.dto.request.AddFoodToCartRequest;
import com.example.icebutler_server.cart.dto.request.RemoveFoodFromCartRequest;
import com.example.icebutler_server.cart.dto.response.CartResponse;
import com.example.icebutler_server.cart.service.CartServiceImpl;
import com.example.icebutler_server.food.dto.response.FoodRes;
import com.example.icebutler_server.global.dto.response.ResponseCustom;
import com.example.icebutler_server.global.dto.response.SwaggerApiSuccess;
import com.example.icebutler_server.global.resolver.Auth;
import com.example.icebutler_server.global.resolver.IsLogin;
import com.example.icebutler_server.global.resolver.LoginStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Tag(name = "Cart", description = "장바구니 API")
@RequestMapping("/carts")
@RestController
public class CartController {

    private final CartServiceImpl cartService;

    @Operation(summary = "장바구니 식품 조회", description = "장바구니 식품 목록을 조회한다.")
    @SwaggerApiSuccess(implementation = CartResponse.class)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "403", description = "냉장고의 멤버가 아닙니다.",
                    content = @Content(schema = @Schema(implementation = ResponseCustom.class))),
            @ApiResponse(responseCode = "404", description = "요청한 id를 가진 유저를 찾을 수 없습니다.\t\n" +
                    "요청한 id를 가진 냉장고를 찾을 수 없습니다.\t\n" +
                    "요청한 id를 가진 장바구니를 찾을 수 없습니다.",
                    content = @Content(schema = @Schema(implementation = ResponseCustom.class))),
    })
    @Auth
    @GetMapping("/{fridgeIdx}/foods")
    public ResponseCustom<List<CartResponse>> getCartFoods(@Parameter(name = "냉장고 ID") @PathVariable Long fridgeIdx,
                                                           @Parameter(hidden = true) @IsLogin LoginStatus loginStatus) {
        return ResponseCustom.OK(cartService.getCartFoods(fridgeIdx, loginStatus.getUserIdx()));
    }

    @Operation(summary = "장바구니 식품 추가", description = "장바구니에 식품을 추가한다.")
    @SwaggerApiSuccess(implementation = ResponseCustom.class)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "403", description = "냉장고의 멤버가 아닙니다.",
                    content = @Content(schema = @Schema(implementation = ResponseCustom.class))),
            @ApiResponse(responseCode = "404", description = "요청한 id를 가진 유저를 찾을 수 없습니다.\t\n" +
                    "요청한 id를 가진 냉장고를 찾을 수 없습니다.\t\n" +
                    "요청한 id를 가진 장바구니를 찾을 수 없습니다.\t\n" +
                    "존재하지 않는 카테고리입니다.",
                    content = @Content(schema = @Schema(implementation = ResponseCustom.class))),
    })
    @Auth
    @PostMapping("/{fridgeIdx}/foods")
    public ResponseCustom<?> addCartFoods(@Parameter(name = "냉장고 ID") @PathVariable Long fridgeIdx,
                                          @RequestBody AddFoodToCartRequest request,
                                          @Parameter(hidden = true) @IsLogin LoginStatus loginStatus) {
        cartService.addCartFoods(fridgeIdx, request, loginStatus.getUserIdx());
        return ResponseCustom.OK();
    }

    @Operation(summary = "장바구니 식품 삭제", description = "장바구니의 식품을 삭제한다.")
    @SwaggerApiSuccess(implementation = ResponseCustom.class)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "403", description = "냉장고의 멤버가 아닙니다.",
                    content = @Content(schema = @Schema(implementation = ResponseCustom.class))),
            @ApiResponse(responseCode = "404", description = "요청한 id를 가진 유저를 찾을 수 없습니다.\t\n" +
                    "요청한 id를 가진 냉장고를 찾을 수 없습니다.\t\n" +
                    "요청한 id를 가진 장바구니를 찾을 수 없습니다.\t\n" +
                    "존재하지 않는 카테고리입니다.",
                    content = @Content(schema = @Schema(implementation = ResponseCustom.class))),
    })
    @Auth
    @DeleteMapping("/{fridgeIdx}/foods")
    public ResponseCustom<?> deleteCartFoods(@Parameter(name = "냉장고 ID") @PathVariable Long fridgeIdx,
                                             @RequestBody RemoveFoodFromCartRequest request,
                                             @Parameter(hidden = true) @IsLogin LoginStatus loginStatus) {
        cartService.deleteCartFoods(fridgeIdx, request, loginStatus.getUserIdx());
        return ResponseCustom.OK();
    }
}
