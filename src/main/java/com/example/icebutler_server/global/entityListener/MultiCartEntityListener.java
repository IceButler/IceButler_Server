package com.example.icebutler_server.global.entityListener;

import com.example.icebutler_server.cart.entity.multiCart.MultiCart;
import com.example.icebutler_server.cart.repository.multiCart.MultiCartFoodRepository;
import com.example.icebutler_server.global.util.BeanUtils;

import javax.persistence.PreRemove;

public class MultiCartEntityListener {

    @PreRemove
    public void onUpdate(MultiCart multiCart){
        MultiCartFoodRepository multiCartFoodRepository = BeanUtils.getBean(MultiCartFoodRepository.class);
        multiCartFoodRepository.deleteByMultiCart(multiCart);
    }
}
