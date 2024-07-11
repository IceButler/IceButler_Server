package com.example.icebutler_server.fridge.dto.response;

import com.example.icebutler_server.fridge.entity.FridgeUser;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@Schema(name = "UpdateMembersRes", description = "냉장고 사용자 변경 정보")
public class UpdateMembersRes {
    @Schema(name = "withDrawMember", description = "냉장고 탈퇴 사용자 목록")
    private List<FridgeUser> withDrawMember;
    @Schema(name = "checkNewMember", description = "냉장고 가입 사용자 목록")
    private List<FridgeUser> checkNewMember;

    @Builder
    public UpdateMembersRes(List<FridgeUser> withDrawMember, List<FridgeUser> checkNewMember) {
        this.withDrawMember = withDrawMember;
        this.checkNewMember = checkNewMember;
    }

    public static UpdateMembersRes toDto(List<FridgeUser> withDrawMember, List<FridgeUser> checkNewMember) {
        return UpdateMembersRes.builder().withDrawMember(withDrawMember).checkNewMember(checkNewMember).build();
    }
}
