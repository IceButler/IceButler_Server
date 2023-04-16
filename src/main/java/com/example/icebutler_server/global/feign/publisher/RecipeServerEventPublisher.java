package com.example.icebutler_server.global.feign.publisher;

import com.example.icebutler_server.user.entity.User;

public interface RecipeServerEventPublisher {
    void addUser(User user);
}
