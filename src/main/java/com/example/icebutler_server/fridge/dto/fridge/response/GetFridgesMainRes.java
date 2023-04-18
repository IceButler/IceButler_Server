package com.example.icebutler_server.fridge.dto.fridge.response;

import com.example.icebutler_server.fridge.entity.fridge.FridgeUser;
import com.example.icebutler_server.fridge.entity.multiFridge.MultiFridgeUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetFridgesMainRes {

  private static final String FRIDGE = "fridge";
  private static final String MULTI_FRIDGE = "multi";
  List<SelectFridgeRes> fridgeList;

  public static GetFridgesMainRes toDto(List<FridgeUser> fridgeUsers, List<MultiFridgeUser> multiFridgeUsers) {
    GetFridgesMainRes getFridgesMainRes = new GetFridgesMainRes();

    getFridgesMainRes.fridgeList = fridgeUsers.stream().map(m -> SelectFridgeRes.toDto(m.getFridge().getFridgeName(), m.getFridge().getFridgeIdx(), FRIDGE)).collect(Collectors.toList());
    getFridgesMainRes.fridgeList.addAll(multiFridgeUsers.stream().map(m -> SelectFridgeRes.toDto(m.getMultiFridge().getFridgeName(), m.getMultiFridge().getMultiFridgeIdx(), MULTI_FRIDGE)).collect(Collectors.toList()));

    return getFridgesMainRes;
  }
}
