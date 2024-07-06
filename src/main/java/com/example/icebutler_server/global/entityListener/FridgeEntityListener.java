package com.example.icebutler_server.global.entityListener;

import com.example.icebutler_server.cart.repository.CartRepository;
import com.example.icebutler_server.fridge.entity.Fridge;
import com.example.icebutler_server.fridge.repository.FridgeFood.FridgeFoodRepository;
import com.example.icebutler_server.fridge.repository.FridgeUserRepository;
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
