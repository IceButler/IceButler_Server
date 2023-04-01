package com.example.icebutler_server.fridge.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class AddFoodToCartRequest {
    private List<Long> addFoodIdxes;
}
