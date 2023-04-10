package com.example.icebutler_server.fridge.dto.fridge.request;

import com.example.icebutler_server.fridge.entity.fridge.FridgeUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FridgeRegisterReq {
  private Long owner;
  private String fridgeName;
  private String fridgeComment;
  private List<FridgeRegisterMembersReq> members;
//  private List<FridgeUser> users;
}
