package com.example.icebutler_server.food.repository;

import com.example.icebutler_server.food.dto.response.FoodRes;
import com.example.icebutler_server.food.entity.Food;
import com.example.icebutler_server.food.entity.FoodCategory;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

import static com.example.icebutler_server.food.entity.QFood.food;

@RequiredArgsConstructor
public class FoodRepositoryImpl implements FoodCustom{
    private final JPAQueryFactory jpaQueryFactory;


    @Override
    public List<Food> searchFood(String category, String word) {
        return jpaQueryFactory
                .selectFrom(food)
                .where(categoryEq(category), nameContains(word))
                .fetch();
    }

    private BooleanExpression categoryEq(String category){
        return StringUtils.isEmpty(category) ? null : food.foodCategory.eq( FoodCategory.getFoodCategoryByName(category));
    }

    private BooleanExpression nameContains(String word){
        return StringUtils.isEmpty(word) ? null : food.foodName.contains(word);
    }
}
