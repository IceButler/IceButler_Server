package com.example.icebutler_server.fridge.dto.fridge.assembler;

import com.example.icebutler_server.fridge.dto.fridge.request.FridgeFoodReq;
import com.example.icebutler_server.fridge.dto.fridge.response.FridgeFoodRes;
import com.example.icebutler_server.fridge.entity.fridge.Fridge;
import com.example.icebutler_server.fridge.entity.fridge.FridgeFood;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

@Component
@RequiredArgsConstructor
public class FridgeFoodAssembler {
    public Fridge toEntity(FridgeFoodReq fridgeFoodReq) {
        return null;
//    return FridgeFood.builder()
//            .food(fridgeFoodReq.ge)
//            .build();
    }

    public FridgeFoodRes toDto(FridgeFood fridgeFood) {
        long day = Math.abs(ChronoUnit.DAYS.between(LocalDate.now(), fridgeFood.getShelfLife()));
        String mark, owner;
        if(fridgeFood.getShelfLife().isAfter(LocalDate.now())) mark = "-";
        else mark = "+";
        if(fridgeFood.getOwner()==null) owner = null;
        else owner = fridgeFood.getOwner().getNickname();

        return FridgeFoodRes.builder()
                .fridgeFoodIdx(fridgeFood.getFridgeFoodIdx())
                .foodIdx(fridgeFood.getFood().getFoodIdx())
                .foodName(fridgeFood.getFood().getFoodName())
                .foodDetailName(fridgeFood.getFoodDetailName())
                .foodCategory(fridgeFood.getFood().getFoodCategory().getName())
                .shelfLife(fridgeFood.getShelfLife().format(DateTimeFormatter.ISO_DATE))
                .day("D"+mark+day)
                .owner(owner)
                .memo(fridgeFood.getMemo())
                .imgUrl(fridgeFood.getFridgeFoodImgKey())
                .build();
    }

    public FridgeFoodRes getFridgeFood(FridgeFood fridgeFood) {
        return toDto(fridgeFood);
    }
}