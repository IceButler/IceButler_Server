package com.example.icebutler_server.cart.repository.cart;

import com.example.icebutler_server.cart.entity.cart.CartFood;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.icebutler_server.cart.entity.cart.QCartFood.cartFood;


@RequiredArgsConstructor
@Repository
public class CartFoodQuerydslRepositoryImpl implements CartFoodQuerydslRepository{

    private final JPAQueryFactory queryFactory;


    @Override
    public List<CartFood> findByCartIdxAndFoodIdxIn(Long cartIdx, List<Long> foodIdxes) {
                return queryFactory
                .selectFrom(cartFood)
                .where(
                        cartFood.cart.cartIdx.eq(cartIdx),
                        cartFood.food.foodIdx.in(foodIdxes)
                )
                .fetch();
    }
}
