package com.example.icebutler_server.cart.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class RemoveFoodFromCartRequest {
    private List<Long> foodIdxes;
}
