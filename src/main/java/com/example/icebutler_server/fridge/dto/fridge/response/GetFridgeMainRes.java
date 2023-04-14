package com.example.icebutler_server.fridge.dto.fridge.response;

import com.example.icebutler_server.fridge.entity.fridge.FridgeUser;
import com.example.icebutler_server.fridge.entity.multiFridge.MultiFridgeUser;
import com.example.icebutler_server.user.entity.User;
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
public class GetFridgeMainRes {
  List<FridgeRes> fridgeList;
  List<MultiFridgeRes> multiFridgeResList;

  public static GetFridgeMainRes toDto(List<FridgeUser> fridgeUsers, List<List<User>> userList, List<MultiFridgeUser> multiFridgeUsers, List<List<User>> userList1) {
    final int[] idx = {0, 0};
    GetFridgeMainRes getFridgeMainRes = new GetFridgeMainRes();
    getFridgeMainRes.fridgeList = fridgeUsers.stream().map(ff -> FridgeRes.toDto(ff.getFridge(), ff.getUser(), userList, idx[0]++)).collect(Collectors.toList());
    getFridgeMainRes.multiFridgeResList = multiFridgeUsers.stream().map(ff -> MultiFridgeRes.toDto(ff.getMultiFridge(), ff.getUser(), userList1, idx[1]++)).collect(Collectors.toList());
    return getFridgeMainRes;
  }
}
