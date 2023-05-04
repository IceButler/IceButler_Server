package com.example.icebutler_server.global.sqs;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@NoArgsConstructor(access= AccessLevel.PROTECTED)
@Getter
public class FoodData {
    private Long foodIdx;
    private String foodName;
    private String foodImgKey;
    private String foodCategory;
}