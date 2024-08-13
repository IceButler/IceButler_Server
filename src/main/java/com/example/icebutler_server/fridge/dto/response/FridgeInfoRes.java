package com.example.icebutler_server.fridge.dto.response;

import com.example.icebutler_server.fridge.entity.Fridge;
import com.example.icebutler_server.fridge.entity.FridgeUser;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@Schema(name = "가정용 냉장고 정보", description = "FridgeInfoRes")
public class FridgeInfoRes {
    @Schema(description = "냉장고 ID", example = "1")
    private Long fridgeId;
    @Schema(description = "냉장고 이름", example = "우리집 냉장고")
    private String fridgeName;
    @Schema(description = "냉장고 설명", example = "환영합니다~")
    private String comment;
    @Schema(description = "냉장고 유저 정보")
    private List<FridgeUserRes> users;
    @Schema(description = "냉장고 유저수", example = "4")
    private int userCnt;

    public static FridgeInfoRes toDto(Fridge fridge, List<FridgeUser> fridgeUsers) {
        return FridgeInfoRes.builder()
                .fridgeId(fridge.getId())
                .fridgeName(fridge.getFridgeName())
                .comment(fridge.getFridgeComment())
                .users(fridgeUsers.stream().map(FridgeUserRes::toDto).collect(Collectors.toList()))
                .userCnt(fridgeUsers.size())
                .build();
    }
}