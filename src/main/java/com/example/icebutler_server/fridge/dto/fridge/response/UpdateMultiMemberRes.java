package com.example.icebutler_server.fridge.dto.fridge.response;

import com.example.icebutler_server.fridge.entity.fridge.FridgeUser;
import com.example.icebutler_server.fridge.entity.multiFridge.MultiFridgeUser;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class UpdateMultiMemberRes {
  private List<MultiFridgeUser> withDrawMember;
  private List<MultiFridgeUser> checkNewMember;

  @Builder
  public UpdateMultiMemberRes(List<MultiFridgeUser> withDrawMember, List<MultiFridgeUser> checkNewMember) {
    this.withDrawMember = withDrawMember;
    this.checkNewMember = checkNewMember;
  }

  public static UpdateMultiMemberRes toDto(List<MultiFridgeUser> withDrawMember, List<MultiFridgeUser> checkNewMember) {
    return UpdateMultiMemberRes.builder().withDrawMember(withDrawMember).checkNewMember(checkNewMember).build();
  }
}
