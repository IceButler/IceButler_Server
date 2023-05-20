package com.example.icebutler_server.admin.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ModifyFoodRequest {
  private String foodCategory;
  private String foodName;
  private String foodImgKey;
}
