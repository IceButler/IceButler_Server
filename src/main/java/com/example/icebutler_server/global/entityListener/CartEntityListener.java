package com.example.icebutler_server.global.entityListener;

import com.example.icebutler_server.cart.entity.cart.Cart;
import com.example.icebutler_server.cart.repository.cart.CartFoodRepository;
import com.example.icebutler_server.global.util.BeanUtils;

import javax.persistence.PreRemove;

public class CartEntityListener {

    @PreRemove
    public void onUpdate(Cart cart){
        CartFoodRepository cartFoodRepository = BeanUtils.getBean(CartFoodRepository.class);
        cartFoodRepository.deleteByCart(cart);
    }
}
