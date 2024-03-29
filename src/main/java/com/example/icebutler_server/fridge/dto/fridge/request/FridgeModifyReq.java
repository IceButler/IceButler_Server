package com.example.icebutler_server.fridge.dto.fridge.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class FridgeModifyReq {
  private String fridgeName;
  private String fridgeComment;
  private List<FridgeModifyMembersReq> members;
  private Long newOwnerIdx;
}
