package com.example.icebutler_server.fridge.dto.response;

import com.example.icebutler_server.fridge.entity.FridgeUser;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(name = "내 냉장고 정보", description = "SelectFridgeRes")
public class MyFridgeRes {
    @Schema(description = "냉장고 ID", example = "1")
    private Long fridgeId;
    @Schema(description = "냉장고 이름", example = "우리집 냉장고")
    private String fridgeName;

    public static MyFridgeRes toDto(FridgeUser fridgeUser) {
        return MyFridgeRes.builder()
                .fridgeId(fridgeUser.getFridge().getId())
                .fridgeName(fridgeUser.getFridge().getFridgeName())
                .build();
    }
}
