package com.example.icebutler_server.cart.dto.cart.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddFoodRequest {
    private String foodName;
    private String foodCategory;
}
