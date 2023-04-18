package com.example.icebutler_server.fridge.repository.fridge.FridgeFood;

import com.example.icebutler_server.food.entity.FoodCategory;
import com.example.icebutler_server.food.entity.FoodDeleteStatus;
import com.example.icebutler_server.fridge.entity.fridge.Fridge;
import com.example.icebutler_server.fridge.entity.multiFridge.MultiFridge;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import static com.example.icebutler_server.fridge.entity.fridge.QFridgeFood.fridgeFood;
import static com.example.icebutler_server.fridge.entity.multiFridge.QMultiFridgeFood.multiFridgeFood;

@RequiredArgsConstructor
public class FridgeFoodRepositoryImpl implements FridgeFoodCustom{
    private final JPAQueryFactory jpaQueryFactory;
    @Override
    public Long findByDeleteCategoryForStatistics(FoodDeleteStatus deleteCategory, Fridge fridge, FoodCategory category, Integer year, Integer month) {
        return jpaQueryFactory.select(fridgeFood.count())
                .from(fridgeFood)
                .where(fridgeFood.foodDeleteStatus.eq(deleteCategory)
                        .and(fridgeFood.fridge.eq(fridge))
                        .and(fridgeFood.food.foodCategory.eq(category))
                        .and(fridgeFood.shelfLife.year().eq(year))
                        .and(fridgeFood.shelfLife.month().eq(month))
                        .and(fridgeFood.isEnable.eq(false)))
                .fetchOne();
    }
}
