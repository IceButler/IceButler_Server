package com.example.icebutler_server.cart.dto.cart.request;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AddFoodToCartRequest {
    private List<AddFoodRequest> foodRequests;

}
