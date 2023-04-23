package com.example.icebutler_server.fridge.dto.fridge.assembler;

import com.example.icebutler_server.food.entity.Food;
import com.example.icebutler_server.food.entity.FoodCategory;
import com.example.icebutler_server.fridge.dto.fridge.request.FridgeFoodReq;
import com.example.icebutler_server.fridge.dto.fridge.response.FridgeFoodRes;
import com.example.icebutler_server.fridge.dto.fridge.response.FridgeFoodStatistics;
import com.example.icebutler_server.fridge.dto.fridge.response.FridgeFoodsStatistics;
import com.example.icebutler_server.fridge.entity.fridge.Fridge;
import com.example.icebutler_server.fridge.entity.fridge.FridgeFood;
import com.example.icebutler_server.global.util.AwsS3ImageUrlUtil;
import com.example.icebutler_server.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.example.icebutler_server.global.util.Constant.FridgeFood.IMG_FOLDER;

@Component
@RequiredArgsConstructor
public class FridgeFoodAssembler {
    public FridgeFood toEntity(User owner, Fridge fridge, Food food, FridgeFoodReq fridgeFoodReq) {
        return FridgeFood.builder()
                .fridge(fridge)
                .food(food)
                .foodDetailName(fridgeFoodReq.getFoodDetailName())
                .shelfLife(LocalDate.parse(fridgeFoodReq.getShelfLife()))
                .owner(owner)
                .memo(fridgeFoodReq.getMemo())
                .fridgeFoodImgKey(IMG_FOLDER + fridgeFoodReq.getImgKey())
                .build();
    }

    public FridgeFoodRes toDto(FridgeFood fridgeFood) {
        String owner;

        if(fridgeFood.getOwner()==null) owner = null;
        else owner = fridgeFood.getOwner().getNickname();

        return FridgeFoodRes.builder()
                .fridgeFoodIdx(fridgeFood.getFridgeFoodIdx())
                .foodIdx(fridgeFood.getFood().getFoodIdx())
                .foodName(fridgeFood.getFood().getFoodName())
                .foodDetailName(fridgeFood.getFoodDetailName())
                .foodCategory(fridgeFood.getFood().getFoodCategory().getName())
                .shelfLife(fridgeFood.getShelfLife().format(DateTimeFormatter.ISO_DATE))
                .day(FridgeUtils.calShelfLife(fridgeFood.getShelfLife()))
                .owner(owner)
                .memo(fridgeFood.getMemo())
                .imgUrl(AwsS3ImageUrlUtil.toUrl(fridgeFood.getFridgeFoodImgKey()))
                .build();
    }

    public void toUpdateFridgeFoodInfo(FridgeFood modifyFood, Food food){
        modifyFood.updateFridgeFoodInfo(food);
    }

    public void toUpdateBasicFridgeFoodInfo(FridgeFood modifyFood, FridgeFoodReq fridgeFoodReq){
        modifyFood.updateFridgeFoodInfo(
                fridgeFoodReq.getFoodDetailName(),
                fridgeFoodReq.getMemo(),
                LocalDate.parse(fridgeFoodReq.getShelfLife()),
                IMG_FOLDER + fridgeFoodReq.getImgKey()
        );
    }

    public FridgeFoodRes getFridgeFood(FridgeFood fridgeFood) {
        return toDto(fridgeFood);
    }

    public void toUpdateFridgeFoodOwner(FridgeFood modifyFridgeFood, User newOwner) {
        modifyFridgeFood.updateMultiFridgeFoodOwner(newOwner);
    }

    public FridgeFoodsStatistics toFoodStatisticsByDeleteStatus(Map<FoodCategory, Long> deleteStatusList) {
        int sum = 0;
        for(Long value : deleteStatusList.values()){
            sum += value.intValue();
        }
        List<FridgeFoodStatistics> foodStatisticsList = new ArrayList<>();

        for(Map.Entry<FoodCategory, Long> deleteStatus: deleteStatusList.entrySet()){
            foodStatisticsList.add(new FridgeFoodStatistics(deleteStatus.getKey().getName(), FridgeUtils.calPercentage(deleteStatus.getValue().intValue(), sum), deleteStatus.getValue().intValue()));
        }
        // sorting
        foodStatisticsList.sort((fs1, fs2) -> (fs2.getCount() - fs1.getCount()));
        return FridgeFoodsStatistics.toDto(foodStatisticsList);
    }
}