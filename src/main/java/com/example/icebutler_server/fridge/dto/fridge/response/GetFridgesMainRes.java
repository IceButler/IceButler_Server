package com.example.icebutler_server.fridge.dto.fridge.response;

import com.example.icebutler_server.fridge.entity.fridge.FridgeUser;
import com.example.icebutler_server.fridge.entity.multiFridge.MultiFridgeUser;
import com.example.icebutler_server.fridge.exception.FridgeUserNotFoundException;
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
  List<FridgeRes> fridgeList;
  List<MultiFridgeRes> multiFridgeResList;

  public static GetFridgesMainRes toDto(List<List<FridgeUser>> fridgeUserListList, List<List<MultiFridgeUser>> multiFridgeUserListList, Long userIdx) {
    GetFridgesMainRes getFridgesMainRes = new GetFridgesMainRes();

    List<FridgeUser> fridgeUsers = fridgeUserListList.stream().map(m -> m.stream().filter(f -> f.getUser().getUserIdx().equals(userIdx)).findAny().orElseThrow(FridgeUserNotFoundException::new)).collect(Collectors.toList());
    getFridgesMainRes.fridgeList = fridgeUsers.stream().map(m -> FridgeRes.toDto(m.getFridge(), fridgeUserListList)).collect(Collectors.toList());

    List<MultiFridgeUser> multiFridgeUsers = multiFridgeUserListList.stream().map(m -> m.stream().filter(f -> f.getUser().getUserIdx().equals(userIdx)).findAny().orElseThrow(FridgeUserNotFoundException::new)).collect(Collectors.toList());
    getFridgesMainRes.multiFridgeResList = multiFridgeUsers.stream().map(m -> MultiFridgeRes.toDto(m.getMultiFridge(), multiFridgeUserListList)).collect(Collectors.toList());

    return getFridgesMainRes;
  }
}
