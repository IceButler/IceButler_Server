package com.example.icebutler_server.global.entityListener;

import com.example.icebutler_server.cart.repository.CartFoodRepository;
import com.example.icebutler_server.food.entity.Food;
import com.example.icebutler_server.fridge.repository.FridgeFood.FridgeFoodRepository;
import com.example.icebutler_server.global.util.BeanUtils;

import javax.persistence.PreRemove;

public class FoodEntityListener {

    @PreRemove
    public void onUpdate(Food food){
        CartFoodRepository cartFoodRepository = BeanUtils.getBean(CartFoodRepository.class);
        cartFoodRepository.deleteByFood(food);
        FridgeFoodRepository fridgeFoodRepository = BeanUtils.getBean(FridgeFoodRepository.class);
        fridgeFoodRepository.deleteByFood(food);
    }
}
