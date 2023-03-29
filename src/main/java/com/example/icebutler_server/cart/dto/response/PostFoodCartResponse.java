package com.example.icebutler_server.cart.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

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
}
