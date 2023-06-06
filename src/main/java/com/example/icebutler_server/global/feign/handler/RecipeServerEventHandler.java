package com.example.icebutler_server.global.feign.handler;

import com.example.icebutler_server.global.feign.event.*;

public interface RecipeServerEventHandler {
    void addUser(UserEvent userEvent);

    void changeUserProfile(UpdateUserEvent userEvent);

    void deleteUser(DeleteUserEvent userEvent);

    void deleteFood(FoodEvent foodEvent);

    void updateFood(UpdateFoodEvent foodEvent);
}
