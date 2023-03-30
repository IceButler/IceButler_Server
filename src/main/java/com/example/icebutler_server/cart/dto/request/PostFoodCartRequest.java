package com.example.icebutler_server.cart.dto.request;

import com.example.icebutler_server.food.entity.Food;
import com.example.icebutler_server.user.entity.User;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PostFoodCartRequest {
    private Integer owner;
    List<Food> foods;
    private int foodIdx;


}
