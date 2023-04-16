package com.example.icebutler_server.global.feign.handler;

import com.example.icebutler_server.global.feign.event.UserJoinEvent;

public interface RecipeServerEventHandler {
    void addUser(UserJoinEvent userJoinEvent);
}
