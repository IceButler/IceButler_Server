package com.example.icebutler_server.fridge.repository;

import com.example.icebutler_server.fridge.entity.CartFood;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.icebutler_server.fridge.entity.QCartFood.cartFood;

@RequiredArgsConstructor
@Repository
public class CartFoodQuerydslRepositoryImpl implements CartFoodQuerydslRepository{

    private final JPAQueryFactory queryFactory;


    @Override
    public List<CartFood> findByCardIdxAndFoodIdxIn(Long cartIdx, List<Long> foodIdxes) {

        return queryFactory
                .selectFrom(cartFood)
                .where(
                        cartFood.cart.cardIdx.eq(cartIdx),
                        cartFood.food.foodIdx.in(foodIdxes)
                )
                .fetch();
    }
}
