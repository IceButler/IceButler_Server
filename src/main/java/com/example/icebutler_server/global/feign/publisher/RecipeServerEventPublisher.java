package com.example.icebutler_server.global.feign.publisher;

import com.example.icebutler_server.food.entity.Food;
import com.example.icebutler_server.user.entity.User;

public interface RecipeServerEventPublisher {
  void addUser(User user);

  void changeUserProfile(User user);

  void deleteUser(User user);

  void deleteFood(Food food);

  void updateFood(Food food);
}
