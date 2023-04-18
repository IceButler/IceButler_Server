package com.example.icebutler_server.fridge.dto.fridge.response;

import com.example.icebutler_server.fridge.entity.fridge.FridgeUser;
import com.example.icebutler_server.fridge.entity.multiFridge.MultiFridgeUser;
import com.example.icebutler_server.global.util.Constant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.icebutler_server.global.util.Constant.FRIDGE;
import static com.example.icebutler_server.global.util.Constant.MULTI_FRIDGE;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SelectFridgesMainRes {

  List<SelectFridgeRes> fridgeList;

  public static SelectFridgesMainRes toDto(List<FridgeUser> fridgeUsers, List<MultiFridgeUser> multiFridgeUsers) {
    SelectFridgesMainRes selectFridgesMainRes = new SelectFridgesMainRes();

    selectFridgesMainRes.fridgeList = fridgeUsers.stream().map(m -> SelectFridgeRes.toDto(m.getFridge().getFridgeName(), m.getFridge().getFridgeIdx(), FRIDGE)).collect(Collectors.toList());
    selectFridgesMainRes.fridgeList.addAll(multiFridgeUsers.stream().map(m -> SelectFridgeRes.toDto(m.getMultiFridge().getFridgeName(), m.getMultiFridge().getMultiFridgeIdx(), MULTI_FRIDGE)).collect(Collectors.toList()));

    return selectFridgesMainRes;
  }
}
