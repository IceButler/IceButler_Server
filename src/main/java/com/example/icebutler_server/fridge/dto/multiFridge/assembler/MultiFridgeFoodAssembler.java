package com.example.icebutler_server.fridge.dto.multiFridge.assembler;

import com.example.icebutler_server.food.entity.Food;
import com.example.icebutler_server.fridge.dto.fridge.request.FridgeFoodReq;
import com.example.icebutler_server.fridge.entity.multiFridge.MultiFridge;
import com.example.icebutler_server.fridge.entity.multiFridge.MultiFridgeFood;
import com.example.icebutler_server.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

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
                .fridgeFoodImgKey(fridgeFoodReq.getImgUrl())
                .build();
    }
}