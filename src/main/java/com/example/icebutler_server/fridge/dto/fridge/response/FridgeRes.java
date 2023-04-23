package com.example.icebutler_server.fridge.dto.fridge.response;

import com.example.icebutler_server.fridge.entity.fridge.Fridge;
import com.example.icebutler_server.fridge.entity.fridge.FridgeUser;
import com.example.icebutler_server.fridge.exception.FridgeUserNotFoundException;
import com.example.icebutler_server.global.entity.FridgeRole;
import com.example.icebutler_server.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.icebutler_server.user.entity.QUser.user;

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
        if (fridgeUser.getFridge().getFridgeIdx().equals(fridge.getFridgeIdx())) fridgeUsers.add(fridgeUser);
      }
    }

    FridgeRes fridgeRes = new FridgeRes();
    fridgeRes.fridgeIdx = fridge.getFridgeIdx();
    fridgeRes.fridgeName = fridge.getFridgeName();
    fridgeRes.comment = fridge.getFridgeComment();
    fridgeRes.users = fridgeUsers.stream().map(m -> FridgeUserRes.toDto(m.getUser(), m.getRole())).collect(Collectors.toList());
    fridgeRes.userCnt = fridgeUsers.size();
    return fridgeRes;
  }
}