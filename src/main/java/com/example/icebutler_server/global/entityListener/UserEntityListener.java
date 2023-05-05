package com.example.icebutler_server.global.entityListener;

import com.example.icebutler_server.fridge.repository.fridge.FridgeUserRepository;
import com.example.icebutler_server.fridge.repository.multiFridge.MultiFridgeUserRepository;
import com.example.icebutler_server.global.util.BeanUtils;
import com.example.icebutler_server.user.entity.User;

import javax.persistence.PreRemove;

public class UserEntityListener {

    @PreRemove
    public void onUpdate(User user){
        FridgeUserRepository fridgeUserRepository = BeanUtils.getBean(FridgeUserRepository.class);
        fridgeUserRepository.deleteByUser(user);
        MultiFridgeUserRepository multiFridgeUserRepository = BeanUtils.getBean(MultiFridgeUserRepository.class);
        multiFridgeUserRepository.deleteByUser(user);
    }
}
