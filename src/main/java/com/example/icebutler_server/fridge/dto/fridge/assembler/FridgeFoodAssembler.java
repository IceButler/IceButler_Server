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
        String imgKey;
        if (fridgeFoodReq.getImgKey() == null) imgKey = null;
        else imgKey = IMG_FOLDER + fridgeFoodReq.getImgKey();

        return FridgeFood.builder()
                .fridge(fridge)
                .food(food)
                .foodDetailName(fridgeFoodReq.getFoodDetailName())
                .shelfLife(LocalDate.parse(fridgeFoodReq.getShelfLife()))
                .owner(owner)
                .memo(fridgeFoodReq.getMemo())
                .fridgeFoodImgKey(imgKey)
                .build();
    }


    public void toUpdateFridgeFoodInfo(FridgeFood modifyFood, Food food){
        modifyFood.updateFridgeFoodInfo(food);
    }

    public void toUpdateBasicFridgeFoodInfo(FridgeFood modifyFood, FridgeFoodReq fridgeFoodReq){
        String imgKey;
        if (fridgeFoodReq.getImgKey() == null) imgKey = null;
        else imgKey = IMG_FOLDER + fridgeFoodReq.getImgKey();
        modifyFood.updateFridgeFoodInfo(
                fridgeFoodReq.getFoodDetailName(),
                fridgeFoodReq.getMemo(),
                LocalDate.parse(fridgeFoodReq.getShelfLife()),
                imgKey
        );
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
            foodStatisticsList.add(new FridgeFoodStatistics(deleteStatus.getKey().getName(),AwsS3ImageUrlUtil.toUrl(deleteStatus.getKey().getImage()) ,FridgeUtils.calPercentage(deleteStatus.getValue().intValue(), sum), deleteStatus.getValue().intValue()));
        }
        // sorting
        foodStatisticsList.sort((fs1, fs2) -> (fs2.getCount() - fs1.getCount()));
        return FridgeFoodsStatistics.toDto(foodStatisticsList);
    }
}