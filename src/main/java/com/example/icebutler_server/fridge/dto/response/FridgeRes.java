package com.example.icebutler_server.fridge.dto.response;

import com.example.icebutler_server.fridge.entity.Fridge;
import com.example.icebutler_server.fridge.entity.FridgeUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FridgeRes {
  private String ownerNickname;
  private String fridgeName;
  private String comment;
  private List<FridgeUserRes> users;

  public static FridgeRes toDto(Fridge fridge)
  {
    FridgeRes fridgeRes = new FridgeRes();
    fridgeRes.ownerNickname = fridge.getOwner().getNickname();
    fridgeRes.fridgeName = fridge.getFridgeName();
    fridgeRes.comment = fridge.getFridgeComment();
    fridgeRes.users = fridge.getFridgeUsers().stream()
            .map((fu) -> FridgeUserRes.toDto(fu.getOwner()))
            .collect(Collectors.toList());

    return fridgeRes;
  }
}
