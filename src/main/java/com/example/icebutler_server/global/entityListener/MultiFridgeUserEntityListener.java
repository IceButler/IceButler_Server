package com.example.icebutler_server.global.entityListener;

import com.example.icebutler_server.cart.repository.multiCart.MultiCartRepository;
import com.example.icebutler_server.fridge.entity.multiFridge.MultiFridgeUser;
import com.example.icebutler_server.fridge.repository.multiFridge.MultiFridgeFood.MultiFridgeFoodRepository;
import com.example.icebutler_server.global.util.BeanUtils;

import javax.persistence.PreRemove;

public class MultiFridgeUserEntityListener {

    @PreRemove
    public void onUpdate(MultiFridgeUser multiFridgeUser){
        MultiFridgeFoodRepository multiFridgeFoodRepository = BeanUtils.getBean(MultiFridgeFoodRepository.class);
        multiFridgeFoodRepository.deleteOwnerByMultiFridgeUser(multiFridgeUser);
        MultiCartRepository multiCartRepository = BeanUtils.getBean(MultiCartRepository.class);
        multiCartRepository.deleteByMultiFridgeUser(multiFridgeUser);
    }
}
