package com.example.icebutler_server.cart.repository;

import com.example.icebutler_server.cart.entity.CartFood;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.icebutler_server.cart.entity.QCartFood.cartFood;

@RequiredArgsConstructor
@Repository
public class CartFoodQuerydslRepositoryImpl implements CartFoodQuerydslRepository{

    private final JPAQueryFactory queryFactory;


    @Override
    public List<CartFood> findByCartIdAndFoodIdIn(Long cartId, List<Long> foodIds) {
                return queryFactory
                .selectFrom(cartFood)
                .where(
                        cartFood.cart.id.eq(cartId),
                        cartFood.food.id.in(foodIds)
                )
                .fetch();
    }
}
