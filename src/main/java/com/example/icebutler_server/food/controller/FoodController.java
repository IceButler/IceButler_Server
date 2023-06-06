package com.example.icebutler_server.food.controller;

import com.example.icebutler_server.cart.dto.cart.request.AddFoodRequest;
import com.example.icebutler_server.food.dto.assembler.FoodAssembler;
import com.example.icebutler_server.food.entity.Food;
import com.example.icebutler_server.food.entity.FoodCategory;
import com.example.icebutler_server.food.repository.FoodRepository;
import com.example.icebutler_server.food.service.FoodServiceImpl;
import com.example.icebutler_server.global.dto.response.ResponseCustom;
import com.example.icebutler_server.global.sqs.AmazonSQSSender;
import com.example.icebutler_server.global.sqs.FoodData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/foods")
@RestController
public class FoodController {

    private final FoodServiceImpl foodService;
    private final AmazonSQSSender amazonSQSSender;

    private final FoodRepository foodRepository;
    private final FoodAssembler foodAssembler;

    // 식품 검색
    @GetMapping("")
    public ResponseCustom<?> searchFood(@RequestParam(required = false) String category, @RequestParam(required = false) String word) {
        if(category != null && word != null) return ResponseCustom.OK(foodService.getAllFoodByCategoryAndWord(category, word));
        else if(category != null) return ResponseCustom.OK(foodService.getAllFoodByCategory(category));
        else if(word != null) return ResponseCustom.OK(foodService.getAllFoodByWord(word));
        else return ResponseCustom.OK(foodService.getAllFood());
    }

    // 식품 바코드 검색
    @GetMapping("/barcode")
    public ResponseCustom<?> searchByBarcode(@RequestParam String code_num) throws IOException, org.json.simple.parser.ParseException {
        return ResponseCustom.OK(foodService.searchByBarcode(code_num));
    }

    @GetMapping("/hihitest")
    public void hihiTest() {

        AddFoodRequest addFoodRequest = new AddFoodRequest();
        addFoodRequest.setFoodName("맛없는 고기");
        addFoodRequest.setFoodCategory("육류");

        Food food = this.foodRepository.save(foodAssembler.toEntity(addFoodRequest));
        FoodData foodData = FoodData.toDto(food);
        String uuid = foodData.getUuid();
        System.out.println("sending food uuid :"+uuid);
        amazonSQSSender.sendMessage(FoodData.toDto(food));
    }
}