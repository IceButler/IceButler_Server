package com.example.icebutler_server.global.feign.handler;

import com.example.icebutler_server.global.feign.event.UserEvent;

public interface RecipeServerEventHandler {
    void addUser(UserEvent userEvent);

    void changeUserProfile(UserEvent userEvent);
}
