package com.example.icebutler_server.fridge.dto.request;

import com.example.icebutler_server.fridge.entity.FridgeUser;
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
  private List<FridgeUser> users;
}
