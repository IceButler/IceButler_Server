package com.example.icebutler_server.fridge.dto.fridge.response;

import com.example.icebutler_server.fridge.entity.fridge.FridgeUser;
import com.example.icebutler_server.fridge.entity.multiFridge.MultiFridgeUser;
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
public class GetFridgesMainRes {
  List<FridgesRes> fridgeList;
  List<MultiFridgesRes> multiFridgeList;

  public static GetFridgesMainRes toDto(List<FridgeUser> fridgeUsers, List<MultiFridgeUser> multiFridgeUsers) {
    GetFridgesMainRes getFridgesMainRes = new GetFridgesMainRes();
    getFridgesMainRes.fridgeList = fridgeUsers.stream().map(ff -> FridgesRes.toDto(ff.getFridge())).collect(Collectors.toList());
    getFridgesMainRes.multiFridgeList = multiFridgeUsers.stream().map(ff -> MultiFridgesRes.toDto(ff.getMultiFridge())).collect(Collectors.toList());
    return getFridgesMainRes;
  }
}
