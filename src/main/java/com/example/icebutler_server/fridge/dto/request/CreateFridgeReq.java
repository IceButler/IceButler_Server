package com.example.icebutler_server.fridge.dto.request;

import com.example.icebutler_server.fridge.entity.FridgeUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateFridgeReq {
  private Long owner;
  private String fridgeName;
  private String description;
  List<FridgeUser> users;
}
