package com.example.icebutler_server.cart.dto.cart.request;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class AddFoodRequest {
    private String foodName;
    private String foodCategory;
}
