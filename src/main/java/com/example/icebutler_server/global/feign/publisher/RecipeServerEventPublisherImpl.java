package com.example.icebutler_server.global.feign.publisher;

import com.example.icebutler_server.global.feign.event.UserEvent;
import com.example.icebutler_server.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RecipeServerEventPublisherImpl implements RecipeServerEventPublisher{

    private final ApplicationEventPublisher publisher;

    @Override
    public void addUser(User user) {
        publisher.publishEvent(UserEvent.toEvent(user));
    }

    @Override
    public void changeUserProfile(User user) {
        publisher.publishEvent(UserEvent.toEvent(user));
    }

    @Override
    public void deleteUser(User user) {
        publisher.publishEvent(UserEvent.toEvent(user));
    }
}
