package com.example.icebutler_server.global.entityListener;

import com.example.icebutler_server.cart.repository.multiCart.MultiCartRepository;
import com.example.icebutler_server.fridge.entity.multiFridge.MultiFridge;
import com.example.icebutler_server.fridge.repository.multiFridge.MultiFridgeFood.MultiFridgeFoodRepository;
import com.example.icebutler_server.fridge.repository.multiFridge.MultiFridgeUserRepository;
import com.example.icebutler_server.global.util.BeanUtils;

import javax.persistence.PreRemove;

public class MultiFridgeEntityListener {

    @PreRemove
    public void onUpdate(MultiFridge multiFridge){
        MultiCartRepository multiCartRepository = BeanUtils.getBean(MultiCartRepository.class);
        multiCartRepository.deleteByMultiFridgeUserMultiFridge(multiFridge);
        MultiFridgeFoodRepository multiFridgeFoodRepository = BeanUtils.getBean(MultiFridgeFoodRepository.class);
        multiFridgeFoodRepository.deleteByMultiFridge(multiFridge);
        MultiFridgeUserRepository multiFridgeUserRepository = BeanUtils.getBean(MultiFridgeUserRepository.class);
        multiFridgeUserRepository.deleteByMultiFridge(multiFridge);
    }
}
