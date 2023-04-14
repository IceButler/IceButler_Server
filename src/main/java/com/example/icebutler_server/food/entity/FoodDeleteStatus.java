package com.example.icebutler_server.food.entity;

import com.example.icebutler_server.food.exception.FoodDeleteStatusNotFoundException;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum FoodDeleteStatus {
    DISCARD("폐기"),
    INGESTION("섭취");

    private final String name;

    private FoodDeleteStatus(String name) {
        this.name=name;
    }

    public static FoodDeleteStatus getFoodCategoryByName(String name){
        return Arrays.stream(FoodDeleteStatus.values())
                .filter(r -> r.getName().equals(name))
                .findAny().orElseThrow(FoodDeleteStatusNotFoundException::new);
    }
}
