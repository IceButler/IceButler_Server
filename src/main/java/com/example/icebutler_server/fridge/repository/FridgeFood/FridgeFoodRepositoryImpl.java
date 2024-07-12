package com.example.icebutler_server.fridge.repository.FridgeFood;

import com.example.icebutler_server.food.entity.Food;
import com.example.icebutler_server.food.entity.FoodCategory;
import com.example.icebutler_server.food.entity.FoodDeleteStatus;
import com.example.icebutler_server.fridge.dto.response.FridgeDiscardRes;
import com.example.icebutler_server.fridge.dto.response.QFridgeDiscardRes;
import com.example.icebutler_server.fridge.entity.Fridge;
import com.example.icebutler_server.fridge.entity.FridgeFood;
import com.example.icebutler_server.fridge.entity.FridgeUser;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;

import static com.example.icebutler_server.food.entity.QFood.food;
import static com.example.icebutler_server.fridge.entity.QFridge.fridge;
import static com.example.icebutler_server.fridge.entity.QFridgeFood.fridgeFood;
import static com.example.icebutler_server.fridge.entity.QFridgeUser.fridgeUser;

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
                        .and(fridgeFood.updatedAt.year().eq(year))
                        .and(fridgeFood.updatedAt.month().eq(month))
                        .and(fridgeFood.isEnable.eq(false)))
                .fetchOne();
    }

    @Override
    public FridgeDiscardRes findByFridgeForDisCardFood(Fridge fridge) {
        LocalDate beginTimePath = LocalDate.now();
        return jpaQueryFactory.select(new QFridgeDiscardRes(fridgeFood.food.foodCategory, fridgeFood.food.foodImgKey))
                .from(fridgeFood)
                .where(fridgeFood.fridge.eq(fridge)
                        .and(fridgeFood.foodDeleteStatus.eq(FoodDeleteStatus.DISCARD))
                        .and(fridgeFood.isEnable.eq(false))
                        .and(fridgeFood.updatedAt.year().eq(beginTimePath.getYear()))
                        .and(fridgeFood.updatedAt.month().eq(beginTimePath.getMonth().getValue())))
                .groupBy(fridgeFood.food.foodCategory)
                .having(fridgeFood.food.foodCategory.count().goe(1L))
                .orderBy(fridgeFood.food.id.count().desc())
                .limit(1)
                .fetchFirst();
    }

    /**
     * select food.food_id, food.food_name
     * from food,
     *      fridge_food as ff, fridge as f, fridge_user as fu,
     *      multi_fridge_food as mff, multi_fridge as mf, multi_fridge_user as mfu
     * where (food.food_id = ff.food_id and ff.fridge_id = f.fridge_id and f.fridge_id = fu.fridge_id
     *            and fu.user_id = 369 and ff.is_enable = true and f.is_enable = true and fu.is_enable = true)
     *   or ((food.food_id = mff.food_id and mff.multi_fridge_id = mf.multi_fridge_id and mf.multi_fridge_id = mfu.multi_fridge_id)
     *       and (mfu.user_id = 369 and mff.is_enable = true and mf.is_enable = true and mfu.is_enable = true))
     * group by food.food_id;
     */

    @Override
    public List<Food> findByUserForFridgeRecipeFoodList(Fridge fridgeEntity) {
        return jpaQueryFactory.selectFrom(food)
                .leftJoin(fridgeFood).on(food.eq(fridgeFood.food))
                .leftJoin(fridge).on(fridgeFood.fridge.eq(fridge))
                .leftJoin(fridgeUser).on(fridgeUser.fridge.eq(fridge))
                .where((fridge.eq(fridgeEntity)).
                        and(fridgeFood.isEnable.eq(true)).and(fridge.isEnable.eq(true)).and(fridgeUser.isEnable.eq(true)))
                .groupBy(food.id)
                .fetch();
    }



    @Override
    public void deleteOwnerByFridgeUser(FridgeUser fridgeUser) {
        jpaQueryFactory.update(fridgeFood)
                .setNull(fridgeFood.owner)
                .where(fridgeFood.owner.eq(fridgeUser.getUser()))
                .execute();
    }

    @Override
    public List<FridgeFood> findByActiveAndShelfLifeLimit3() {
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusDays(3);
       return jpaQueryFactory.selectFrom(fridgeFood)
                .where(fridgeFood.isEnable.eq(true)
                        .and(fridgeFood.shelfLife.between(startDate, endDate)))
               .fetch();

    }
}
