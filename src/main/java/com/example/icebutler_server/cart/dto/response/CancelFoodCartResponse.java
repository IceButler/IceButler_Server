package com.example.icebutler_server.cart.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class CancelFoodCartResponse {
    private String foodName;
    private Long cartIdx;
    private int foodIdx;
    private String message;
}
