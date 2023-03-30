package com.example.icebutler_server.fridge.dto.request;

import com.example.icebutler_server.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateFridgeReq {
  private Long fridgeId;
  private String fridgeName;
  private String fridgeComment;
  private List<String> usersName;
  private String newOwnerName;
}
