package com.example.icebutler_server.global.entityListener;

import com.example.icebutler_server.cart.repository.cart.CartFoodRepository;
import com.example.icebutler_server.cart.repository.multiCart.MultiCartFoodRepository;
import com.example.icebutler_server.food.entity.Food;
import com.example.icebutler_server.fridge.repository.fridge.FridgeFood.FridgeFoodRepository;
import com.example.icebutler_server.fridge.repository.multiFridge.MultiFridgeFood.MultiFridgeFoodRepository;
import com.example.icebutler_server.global.util.BeanUtils;

import javax.persistence.PreRemove;

public class FoodEntityListener {

    @PreRemove
    public void onUpdate(Food food){
        CartFoodRepository cartFoodRepository = BeanUtils.getBean(CartFoodRepository.class);
        cartFoodRepository.deleteByFood(food);
        MultiCartFoodRepository multiCartFoodRepository = BeanUtils.getBean(MultiCartFoodRepository.class);
        multiCartFoodRepository.deleteByFood(food);
        FridgeFoodRepository fridgeFoodRepository = BeanUtils.getBean(FridgeFoodRepository.class);
        fridgeFoodRepository.deleteByFood(food);
        MultiFridgeFoodRepository multiFridgeFoodRepository = BeanUtils.getBean(MultiFridgeFoodRepository.class);
        multiFridgeFoodRepository.deleteByFood(food);
    }
}
