package com.example.icebutler_server.cart.dto.cart.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AddFoodToCartRequest {
    private List<AddFoodRequest> foodRequests;

}
