package com.example.icebutler_server.global.feign.handler;

import com.example.icebutler_server.global.feign.event.FoodEvent;
import com.example.icebutler_server.global.feign.event.UserEvent;
import com.example.icebutler_server.global.feign.feignClient.RecipeServerClient;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class RecipeServerEventHandlerImpl implements RecipeServerEventHandler{
    private final RecipeServerClient recipeServerClient;

    @Async
    @EventListener
    @Override
    public void addUser(UserEvent userEvent) {
        try {
            recipeServerClient.addUser(userEvent.toDto());
        } catch (FeignException e) {
            log.error(e.getMessage());
        }
    }

    @Async
    @EventListener
    @Override
    public void changeUserProfile(UserEvent userEvent) {
        try {
            recipeServerClient.changeUser(userEvent.toDto());
        } catch (FeignException e) {
            log.error(e.getMessage());
        }
    }

    @Async
    @EventListener
    @Override
    public void deleteUser(UserEvent userEvent) {
        recipeServerClient.deleteUser(userEvent.toDto());
    }

    @Async
    @EventListener
    @Override
    public void deleteFood(FoodEvent foodEvent) {
        recipeServerClient.deleteFood(foodEvent.toDto());
    }
}
