package com.example.icebutler_server.fridge.repository.multiFridge.MultiFridgeFood;

import com.example.icebutler_server.food.entity.FoodCategory;
import com.example.icebutler_server.food.entity.FoodDeleteStatus;
import com.example.icebutler_server.fridge.entity.multiFridge.MultiFridge;
import com.example.icebutler_server.fridge.entity.multiFridge.MultiFridgeFood;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.example.icebutler_server.fridge.entity.multiFridge.QMultiFridgeFood.multiFridgeFood;

@RequiredArgsConstructor
public class MultiFridgeFoodRepositoryImpl implements MultiFridgeFoodCustom{
    private final JPAQueryFactory jpaQueryFactory;
    @Override
    public Long findByDeleteCategoryForStatistics(FoodDeleteStatus deleteCategory, MultiFridge multiFridge, FoodCategory category, Integer year, Integer month) {
        return jpaQueryFactory.select(multiFridgeFood.count())
                .from(multiFridgeFood)
                .where(multiFridgeFood.foodDeleteStatus.eq(deleteCategory)
                        .and(multiFridgeFood.multiFridge.eq(multiFridge))
                        .and(multiFridgeFood.food.foodCategory.eq(category))
                        .and(multiFridgeFood.shelfLife.year().eq(year))
                        .and(multiFridgeFood.shelfLife.month().eq(month))
                        .and(multiFridgeFood.isEnable.eq(true)))
                .fetchOne();
    }
}
