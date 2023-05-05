package com.example.icebutler_server.fridge.repository.multiFridge.MultiFridgeFood;

import com.example.icebutler_server.food.entity.FoodCategory;
import com.example.icebutler_server.food.entity.FoodDeleteStatus;
import com.example.icebutler_server.fridge.dto.fridge.response.FridgeDiscardRes;
import com.example.icebutler_server.fridge.dto.fridge.response.QFridgeDiscardRes;
import com.example.icebutler_server.fridge.entity.multiFridge.MultiFridge;
import com.example.icebutler_server.fridge.entity.multiFridge.MultiFridgeUser;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

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
                        .and(multiFridgeFood.updateAt.year().eq(year))
                        .and(multiFridgeFood.updateAt.month().eq(month))
                        .and(multiFridgeFood.isEnable.eq(false)))
                .fetchOne();
    }

    @Override
    public FridgeDiscardRes findByMultiFridgeForDisCardFood(MultiFridge fridge) {
        LocalDate beginTimePath = LocalDate.now();
        return jpaQueryFactory.select(new QFridgeDiscardRes(multiFridgeFood.food.foodCategory, multiFridgeFood.food.foodImgKey))
                .from(multiFridgeFood)
                .where(multiFridgeFood.multiFridge.eq(fridge)
                        .and(multiFridgeFood.foodDeleteStatus.eq(FoodDeleteStatus.DISCARD))
                        .and(multiFridgeFood.isEnable.eq(false))
                        .and(multiFridgeFood.updateAt.year().eq(beginTimePath.getYear()))
                        .and(multiFridgeFood.updateAt.month().eq(beginTimePath.getMonth().getValue())))
                .groupBy(multiFridgeFood.food.foodCategory, multiFridgeFood.food.foodImgKey)
                .having(multiFridgeFood.food.foodCategory.count().goe(1L))
                .orderBy(multiFridgeFood.food.foodIdx.count().desc())
                .limit(1)
                .fetchFirst();
    }

    @Override
    public void deleteOwnerByMultiFridgeUser(MultiFridgeUser multiFridgeUser) {
        jpaQueryFactory.update(multiFridgeFood)
                .setNull(multiFridgeFood.owner)
                .where(multiFridgeFood.owner.eq(multiFridgeUser.getUser()))
                .execute();
    }
}
