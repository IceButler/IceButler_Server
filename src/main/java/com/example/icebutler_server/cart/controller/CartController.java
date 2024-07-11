package com.example.icebutler_server.cart.controller;

import com.example.icebutler_server.cart.dto.request.AddFoodToCartRequest;
import com.example.icebutler_server.cart.dto.request.RemoveFoodFromCartRequest;
import com.example.icebutler_server.cart.dto.response.CartResponse;
import com.example.icebutler_server.cart.service.CartServiceImpl;
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
            @ApiResponse(responseCode = "403", description = "(G0001)권한이 없습니다.",
                    content = @Content(schema = @Schema(implementation = ResponseCustom.class))),
            @ApiResponse(responseCode = "404", description = "(U0000)존재하지 않는 사용자입니다.\t\n" +
                    "(R0000)존재하지 않는 냉장고입니다.\t\n" +
                    "(C0000)존재하지 않는 장바구니입니다.",
                    content = @Content(schema = @Schema(implementation = ResponseCustom.class))),
    })
    @Auth
    @GetMapping("/{fridgeId}/foods")
    public ResponseCustom<List<CartResponse>> getCartFoods(@Parameter(name = "냉장고 ID") @PathVariable Long fridgeId,
                                                           @Parameter(hidden = true) @IsLogin LoginStatus loginStatus) {
        return ResponseCustom.success(cartService.getCartFoods(fridgeId, loginStatus.getUserId()));
    }

    @Operation(summary = "장바구니 식품 추가", description = "장바구니에 식품을 추가한다.")
    @SwaggerApiSuccess(implementation = ResponseCustom.class)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "(F0000)존재하지 않는 카테고리입니다.",
                    content = @Content(schema = @Schema(implementation = ResponseCustom.class))),
            @ApiResponse(responseCode = "403", description = "(G0001)권한이 없습니다.",
                    content = @Content(schema = @Schema(implementation = ResponseCustom.class))),
            @ApiResponse(responseCode = "404", description = "(U0000)존재하지 않는 사용자입니다.\t\n" +
                    "(R0000)존재하지 않는 냉장고입니다.\t\n" +
                    "(C0000)존재하지 않는 장바구니입니다.",
                    content = @Content(schema = @Schema(implementation = ResponseCustom.class))),
    })
    @Auth
    @PostMapping("/{fridgeId}/foods")
    public ResponseCustom<?> addCartFoods(@Parameter(name = "냉장고 ID") @PathVariable Long fridgeId,
                                          @RequestBody AddFoodToCartRequest request,
                                          @Parameter(hidden = true) @IsLogin LoginStatus loginStatus) {
        cartService.addCartFoods(fridgeId, request, loginStatus.getUserId());
        return ResponseCustom.success();
    }

    @Operation(summary = "장바구니 식품 삭제", description = "장바구니의 식품을 삭제한다.")
    @SwaggerApiSuccess(implementation = ResponseCustom.class)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "(F0000)존재하지 않는 카테고리입니다.",
                    content = @Content(schema = @Schema(implementation = ResponseCustom.class))),
            @ApiResponse(responseCode = "403", description = "(G0001)권한이 없습니다.",
                    content = @Content(schema = @Schema(implementation = ResponseCustom.class))),
            @ApiResponse(responseCode = "404", description = "(U0000)존재하지 않는 사용자입니다.\t\n" +
                    "(R0000)존재하지 않는 냉장고입니다.\t\n" +
                    "(C0000)존재하지 않는 장바구니입니다.",
                    content = @Content(schema = @Schema(implementation = ResponseCustom.class))),
    })
    @Auth
    @DeleteMapping("/{fridgeId}/foods")
    public ResponseCustom<?> deleteCartFoods(@Parameter(name = "냉장고 ID") @PathVariable Long fridgeId,
                                             @RequestBody RemoveFoodFromCartRequest request,
                                             @Parameter(hidden = true) @IsLogin LoginStatus loginStatus) {
        cartService.deleteCartFoods(fridgeId, request, loginStatus.getUserId());
        return ResponseCustom.success();
    }
}
