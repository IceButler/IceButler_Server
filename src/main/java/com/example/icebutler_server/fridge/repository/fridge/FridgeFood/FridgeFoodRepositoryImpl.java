package com.example.icebutler_server.fridge.repository.fridge.FridgeFood;

import com.example.icebutler_server.food.entity.Food;
import com.example.icebutler_server.food.entity.FoodCategory;
import com.example.icebutler_server.food.entity.FoodDeleteStatus;
import com.example.icebutler_server.fridge.entity.fridge.Fridge;
import com.example.icebutler_server.fridge.entity.multiFridge.MultiFridge;
import com.example.icebutler_server.user.entity.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.example.icebutler_server.food.entity.QFood.food;
import static com.example.icebutler_server.fridge.entity.fridge.QFridge.fridge;
import static com.example.icebutler_server.fridge.entity.fridge.QFridgeFood.fridgeFood;
import static com.example.icebutler_server.fridge.entity.fridge.QFridgeUser.fridgeUser;
import static com.example.icebutler_server.fridge.entity.multiFridge.QMultiFridge.multiFridge;
import static com.example.icebutler_server.fridge.entity.multiFridge.QMultiFridgeFood.multiFridgeFood;
import static com.example.icebutler_server.fridge.entity.multiFridge.QMultiFridgeUser.multiFridgeUser;

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

    /**
     * select food.food_idx, food.food_name
     * from food,
     *      fridge_food as ff, fridge as f, fridge_user as fu,
     *      multi_fridge_food as mff, multi_fridge as mf, multi_fridge_user as mfu
     * where (food.food_idx = ff.food_idx and ff.fridge_idx = f.fridge_idx and f.fridge_idx = fu.fridge_idx
     *            and fu.user_idx = 369 and ff.is_enable = true and f.is_enable = true and fu.is_enable = true)
     *   or ((food.food_idx = mff.food_idx and mff.multi_fridge_idx = mf.multi_fridge_idx and mf.multi_fridge_idx = mfu.multi_fridge_idx)
     *       and (mfu.user_idx = 369 and mff.is_enable = true and mf.is_enable = true and mfu.is_enable = true))
     * group by food.food_idx;
     */

    @Override
    public List<Food> findByUserForFridgeRecipeFoodList(Fridge fridgeEntity) {
        return jpaQueryFactory.selectFrom(food)
                .leftJoin(fridgeFood).on(food.eq(fridgeFood.food))
                .leftJoin(fridge).on(fridgeFood.fridge.eq(fridge))
                .leftJoin(fridgeUser).on(fridgeUser.fridge.eq(fridge))
                .where((fridge.eq(fridgeEntity)).
                        and(fridgeFood.isEnable.eq(true)).and(fridge.isEnable.eq(true)).and(fridgeUser.isEnable.eq(true)))
                .groupBy(food.foodIdx)
                .fetch();
    }

    @Override
    public List<Food> findByUserForMultiFridgeRecipeFoodList(MultiFridge fridgeEntity) {
        return jpaQueryFactory.selectFrom(food)
                .leftJoin(multiFridgeFood).on(food.eq(multiFridgeFood.food))
                .leftJoin(multiFridge).on(multiFridgeFood.multiFridge.eq(multiFridge))
                .leftJoin(multiFridgeUser).on(multiFridgeUser.multiFridge.eq(multiFridge))
                .where((multiFridge.eq(fridgeEntity)).
                        and(multiFridgeFood.isEnable.eq(true)).and(multiFridge.isEnable.eq(true)).and(multiFridgeUser.isEnable.eq(true)))
                .groupBy(food.foodIdx)
                .fetch();
    }
}
