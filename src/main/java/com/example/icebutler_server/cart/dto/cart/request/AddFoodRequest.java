package com.example.icebutler_server.cart.dto.cart.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AddFoodRequest {
    private String foodName;
    private String foodCategory;
}
