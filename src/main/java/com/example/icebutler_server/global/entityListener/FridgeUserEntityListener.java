package com.example.icebutler_server.global.entityListener;

import com.example.icebutler_server.fridge.entity.fridge.FridgeUser;
import com.example.icebutler_server.fridge.repository.fridge.FridgeFood.FridgeFoodRepository;
import com.example.icebutler_server.global.util.BeanUtils;

import javax.persistence.PreRemove;

public class FridgeUserEntityListener {

    @PreRemove
    public void onUpdate(FridgeUser fridgeUser){
        FridgeFoodRepository fridgeFoodRepository = BeanUtils.getBean(FridgeFoodRepository.class);
        fridgeFoodRepository.deleteOwnerByFridgeUser(fridgeUser);
    }
}
