package com.example.icebutler_server.fridge.dto;

import com.example.icebutler_server.fridge.entity.FridgeUser;
import com.example.icebutler_server.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddFridgeReq {
  private Integer onwer;
  private String fridgeName;
  private String description;
  List<FridgeUser> users;
}
