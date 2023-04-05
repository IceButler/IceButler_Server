package com.example.icebutler_server.fridge.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FridgeRes {
  private String ownerNickname;
  private String fridgeName;
  private String comment;
  private List<FridgeUserRes> users;
}
