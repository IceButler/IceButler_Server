package com.example.icebutler_server.fridge.dto.fridge.response;

import com.example.icebutler_server.fridge.entity.fridge.Fridge;
import com.example.icebutler_server.fridge.entity.fridge.FridgeUser;
import com.example.icebutler_server.fridge.entity.multiFridge.MultiFridge;
import com.example.icebutler_server.fridge.entity.multiFridge.MultiFridgeUser;
import com.example.icebutler_server.global.entity.FridgeRole;
import com.example.icebutler_server.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MultiFridgeRes {
  private String ownerNickname;
  private String multiFridgeName;
  private String comment;
  private List<FridgeUserRes> users;

  public static MultiFridgeRes toDto(MultiFridge multiFridge, List<List<MultiFridgeUser>> multiFridgeUserList) {
    List<MultiFridgeUser> multiFridgeUsers = new ArrayList<>();
    for (List<MultiFridgeUser> multiFridgeUserArr : multiFridgeUserList) {
      for (MultiFridgeUser multiFridgeUser : multiFridgeUserArr) {
        if (multiFridgeUser.getMultiFridge().getMultiFridgeIdx().equals(multiFridge.getMultiFridgeIdx())) multiFridgeUsers.add(multiFridgeUser);
      }
    }

    MultiFridgeRes multiFridgeRes = new MultiFridgeRes();
    multiFridgeRes.ownerNickname = multiFridgeUsers.stream().filter(f -> f.getRole().equals(FridgeRole.OWNER)).findAny().get().getUser().getNickname();
    multiFridgeRes.multiFridgeName = multiFridge.getFridgeName();
    multiFridgeRes.comment = multiFridge.getFridgeComment();
    multiFridgeRes.users = multiFridgeUsers.stream().map(m -> FridgeUserRes.toDto(m.getUser())).collect(Collectors.toList());

    return multiFridgeRes;
  }
}
