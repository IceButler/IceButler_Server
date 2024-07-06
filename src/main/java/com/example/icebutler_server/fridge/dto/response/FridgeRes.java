package com.example.icebutler_server.fridge.dto.response;

import com.example.icebutler_server.fridge.entity.Fridge;
import com.example.icebutler_server.fridge.entity.FridgeUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FridgeRes {
  private Long fridgeIdx;
  private String fridgeName;
  private String comment;
  private List<FridgeUserRes> users;
  private Integer userCnt;

  public static FridgeRes toDto(Fridge fridge, List<List<FridgeUser>> fridgeUserList) {
    List<FridgeUser> fridgeUsers = new ArrayList<>();
    for (List<FridgeUser> fridgeArr : fridgeUserList) {
      for (FridgeUser fridgeUser : fridgeArr) {
        if (fridgeUser.getFridge().getId().equals(fridge.getId())) fridgeUsers.add(fridgeUser);
      }
    }

    FridgeRes fridgeRes = new FridgeRes();
    fridgeRes.fridgeIdx = fridge.getId();
    fridgeRes.fridgeName = fridge.getFridgeName();
    fridgeRes.comment = fridge.getFridgeComment();
    fridgeRes.users = fridgeUsers.stream().map(m -> FridgeUserRes.toDto(m.getUser(), m.getRole())).collect(Collectors.toList());
    fridgeRes.userCnt = fridgeUsers.size();
    return fridgeRes;
  }
}