package com.example.icebutler_server.fridge.dto.response;

import com.example.icebutler_server.fridge.entity.FridgeUser;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class UpdateMembersRes {
  private List<FridgeUser> withDrawMember;
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
