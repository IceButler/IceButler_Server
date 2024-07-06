package com.example.icebutler_server.fridge.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FridgeRegisterMembersReq {
  private Long userIdx;
}
