package com.example.icebutler_server.food.controller;

import com.example.icebutler_server.cart.dto.request.AddFoodRequest;
import com.example.icebutler_server.food.dto.response.BarcodeFoodRes;
import com.example.icebutler_server.food.dto.response.FoodRes;
import com.example.icebutler_server.food.entity.Food;
import com.example.icebutler_server.food.repository.FoodRepository;
import com.example.icebutler_server.food.service.FoodServiceImpl;
import com.example.icebutler_server.global.dto.response.ResponseCustom;
import com.example.icebutler_server.global.dto.response.SwaggerApiSuccess;
import com.example.icebutler_server.global.sqs.AmazonSQSSender;
import com.example.icebutler_server.global.sqs.FoodData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Tag(name = "Food", description = "식품 API")
@RequestMapping("/foods")
@RestController
public class FoodController {

    private final FoodServiceImpl foodService;
    private final AmazonSQSSender amazonSQSSender;

    private final FoodRepository foodRepository;

    @Operation(summary = "식품 검색", description = "식품을 검색한다.")
    @SwaggerApiSuccess(implementation = FoodRes.class)
    @ApiResponse(responseCode = "400", description = "(F0000)존재하지 않는 카테고리입니다.",
            content = @Content(schema = @Schema(implementation = ResponseCustom.class)))
    @GetMapping("")
    public ResponseCustom<List<FoodRes>> searchFood(@Parameter(name = "category", description = "식품 카테고리") @RequestParam(required = false) String category,
                                                    @Parameter(name = "word", description = "검색어") @RequestParam(required = false) String word) {
        if (category != null && word != null)
            return ResponseCustom.success(foodService.getAllFoodByCategoryAndWord(category, word));
        else if (category != null) return ResponseCustom.success(foodService.getAllFoodByCategory(category));
        else if (word != null) return ResponseCustom.success(foodService.getAllFoodByWord(word));
        else return ResponseCustom.success(foodService.getAllFood());
    }

    @Operation(summary = "식품 바코드 조회", description = "바코드 번호로 식품을 조회한다.")
    @SwaggerApiSuccess(implementation = BarcodeFoodRes.class)
    @ApiResponse(responseCode = "404", description = "(F0001)해당 바코드의 상품을 찾을 수 없습니다.",
            content = @Content(schema = @Schema(implementation = ResponseCustom.class)))
    @GetMapping("/barcode")
    public ResponseCustom<BarcodeFoodRes> searchByBarcode(@RequestParam String code_num) throws IOException, org.json.simple.parser.ParseException {
        return ResponseCustom.success(foodService.searchByBarcode(code_num));
    }

    @GetMapping("/hihitest")
    public void hihiTest() {

        AddFoodRequest addFoodRequest = new AddFoodRequest();
        addFoodRequest.setFoodName("맛없는 고기");
        addFoodRequest.setFoodCategory("육류");

        Food food = this.foodRepository.save(Food.toEntity(addFoodRequest));
        FoodData foodData = FoodData.toDto(food);
        amazonSQSSender.sendMessage(FoodData.toDto(food));
    }
}