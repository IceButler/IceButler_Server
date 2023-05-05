package com.example.icebutler_server.global.entityListener;

import com.example.icebutler_server.cart.repository.cart.CartRepository;
import com.example.icebutler_server.fridge.entity.fridge.Fridge;
import com.example.icebutler_server.fridge.repository.fridge.FridgeFood.FridgeFoodRepository;
import com.example.icebutler_server.fridge.repository.fridge.FridgeUserRepository;
import com.example.icebutler_server.global.util.BeanUtils;

import javax.persistence.PreRemove;

public class FridgeEntityListener {

    @PreRemove
    public void onUpdate(Fridge fridge){
        CartRepository cartRepository = BeanUtils.getBean(CartRepository.class);
        cartRepository.deleteByFridge(fridge);
        FridgeFoodRepository fridgeFoodRepository = BeanUtils.getBean(FridgeFoodRepository.class);
        fridgeFoodRepository.deleteByFridge(fridge);
        FridgeUserRepository fridgeUserRepository = BeanUtils.getBean(FridgeUserRepository.class);
        fridgeUserRepository.deleteByFridge(fridge);
    }
}
