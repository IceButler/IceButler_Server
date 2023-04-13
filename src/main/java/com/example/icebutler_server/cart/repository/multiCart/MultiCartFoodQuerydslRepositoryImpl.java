package com.example.icebutler_server.cart.repository.multiCart;

import com.example.icebutler_server.cart.entity.multiCart.MultiCartFood;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;


import static com.example.icebutler_server.cart.entity.multiCart.QMultiCartFood.multiCartFood;

@RequiredArgsConstructor
@Repository
public class MultiCartFoodQuerydslRepositoryImpl implements MultiCartFoodQuerydslRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<MultiCartFood> findByCartIdxAndFoodIdxInAndIsEnable(Long cartIdx, List<Long> foodIdxes, Boolean status) {
        return queryFactory
                .selectFrom(multiCartFood)
                .where(
                        multiCartFood.multiCart.multiCartIdx.eq(cartIdx),
                        multiCartFood.food.foodIdx.in(foodIdxes),
                        multiCartFood.isEnable.eq(status)
                )
                .fetch();
    }
}
