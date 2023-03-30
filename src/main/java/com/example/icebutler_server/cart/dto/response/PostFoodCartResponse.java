package com.example.icebutler_server.cart.dto.response;

import com.example.icebutler_server.cart.entity.Cart;
import com.example.icebutler_server.food.entity.Food;
import com.example.icebutler_server.user.entity.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class PostFoodCartResponse {
    private String foodName;
    private int foodIdx;
    private long cartIdx;
    private String message;
    private List<Food> foods;
    private User user;


    public static PostFoodCartResponse toDto(Cart cart){
        PostFoodCartResponse postFoodCartResponse=new PostFoodCartResponse();
        postFoodCartResponse.foods=cart.getFoods();
        postFoodCartResponse.cartIdx=cart.getCardIdx();
        postFoodCartResponse.user= cart.getOwner();

        return postFoodCartResponse;
    }
}
