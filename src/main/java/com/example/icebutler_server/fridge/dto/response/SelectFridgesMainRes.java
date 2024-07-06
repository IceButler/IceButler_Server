package com.example.icebutler_server.fridge.dto.response;

import com.example.icebutler_server.fridge.entity.FridgeUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SelectFridgesMainRes {

  List<SelectFridgeRes> fridgeList;

  public static SelectFridgesMainRes toDto(List<FridgeUser> fridgeUsers) {
    SelectFridgesMainRes selectFridgesMainRes = new SelectFridgesMainRes();

    selectFridgesMainRes.fridgeList = fridgeUsers.stream().map(m -> SelectFridgeRes.toDto(m.getFridge().getFridgeName(), m.getFridge().getId())).collect(Collectors.toList());

    return selectFridgesMainRes;
  }
}
