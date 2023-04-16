package com.example.icebutler_server.global.feign.handler;

import com.example.icebutler_server.global.feign.dto.AddUserReq;
import com.example.icebutler_server.global.feign.event.UserJoinEvent;
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
    public void addUser(UserJoinEvent userJoinEvent) {
        try {
            recipeServerClient.addUser(userJoinEvent.toDto());
        } catch (FeignException e) {
            log.error(e.getMessage());
        }
    }
}
