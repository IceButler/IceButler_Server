package com.example.icebutler_server.fridge.dto.fridge.response;

import com.example.icebutler_server.fridge.entity.fridge.Fridge;
import com.example.icebutler_server.fridge.entity.fridge.FridgeUser;
import com.example.icebutler_server.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FridgeRes {
  private String fridgeName;
  private String ownerNickname;
  private List<FridgeUserRes> users;
  private String comment;


  public static FridgeRes toDto(Fridge fridge,User user
          , List<FridgeUser> fridgeUserList

  ){
    FridgeRes fridgeRes=new FridgeRes();
//    fridgeRes.users=user.stream().map(ff->FridgeUserRes.toDto(ff.getUser())).collect(Collectors.toList());
  fridgeRes.ownerNickname=user.getNickname();
    fridgeRes.fridgeName=fridge.getFridgeName();
    fridgeRes.users=fridgeUserList.stream().map(cf->FridgeUserRes.toDto(cf.getUser())).collect(Collectors.toList());
    fridgeRes.comment=fridge.getFridgeComment();
    return fridgeRes;
  }
}
