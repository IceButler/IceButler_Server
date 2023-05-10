package com.example.icebutler_server.fridge.dto.multiFridge.assembler;

import com.example.icebutler_server.food.entity.Food;
import com.example.icebutler_server.food.entity.FoodCategory;
import com.example.icebutler_server.fridge.dto.fridge.assembler.FridgeUtils;
import com.example.icebutler_server.fridge.dto.fridge.request.FridgeFoodReq;
import com.example.icebutler_server.fridge.dto.fridge.response.FridgeFoodStatistics;
import com.example.icebutler_server.fridge.dto.fridge.response.FridgeFoodsStatistics;
import com.example.icebutler_server.fridge.entity.multiFridge.MultiFridge;
import com.example.icebutler_server.fridge.entity.multiFridge.MultiFridgeFood;
import com.example.icebutler_server.global.util.AwsS3ImageUrlUtil;
import com.example.icebutler_server.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.example.icebutler_server.global.util.Constant.FridgeFood.IMG_FOLDER;

@Component
@RequiredArgsConstructor
public class MultiFridgeFoodAssembler {
    public MultiFridgeFood toEntity(User owner, MultiFridge fridge, Food food, FridgeFoodReq fridgeFoodReq) {
        return MultiFridgeFood.builder()
                .multiFridge(fridge)
                .food(food)
                .foodDetailName(fridgeFoodReq.getFoodDetailName())
                .shelfLife(LocalDate.parse(fridgeFoodReq.getShelfLife()))
                .owner(owner)
                .memo(fridgeFoodReq.getMemo())
                .fridgeFoodImgKey(IMG_FOLDER + fridgeFoodReq.getImgKey())
                .build();
    }

    public void toUpdateBasicMultiFridgeFoodInfo(MultiFridgeFood modifyFood, FridgeFoodReq fridgeFoodReq) {
        modifyFood.updateBasicMultiFridgeFoodInfo(
                fridgeFoodReq.getFoodDetailName(),
                fridgeFoodReq.getMemo(),
                LocalDate.parse(fridgeFoodReq.getShelfLife()),
                IMG_FOLDER + fridgeFoodReq.getImgKey());
    }

    public void toUpdateMultiFridgeFoodInfo(MultiFridgeFood modifyFood, Food food) {
        modifyFood.toUpdateMultiFridgeFoodInfo(food);
    }

    public void toUpdateMultiFridgeFoodOwner(MultiFridgeFood modifyFood, User newOwner) {
        modifyFood.toUpdateMultiFridgeFoodOwner(newOwner);
    }

    public FridgeFoodsStatistics toFoodStatisticsByDeleteStatus(Map<FoodCategory, Long> deleteStatusList) {
        int sum = 0;
        for(Long value : deleteStatusList.values()){
            sum += value.intValue();
        }
        List<FridgeFoodStatistics> foodStatisticsList = new ArrayList<>();

        for(Map.Entry<FoodCategory, Long> deleteStatus: deleteStatusList.entrySet()){
            foodStatisticsList.add(new FridgeFoodStatistics(deleteStatus.getKey().getName(), AwsS3ImageUrlUtil.toUrl(deleteStatus.getKey().getImage()) , FridgeUtils.calPercentage(deleteStatus.getValue().intValue(), sum), deleteStatus.getValue().intValue()));
        }
        // sorting
        foodStatisticsList.sort((fs1, fs2) -> (fs2.getCount() - fs1.getCount()));
        return FridgeFoodsStatistics.toDto(foodStatisticsList);
    }
}