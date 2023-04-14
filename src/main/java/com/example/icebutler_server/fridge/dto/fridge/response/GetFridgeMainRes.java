package com.example.icebutler_server.fridge.dto.fridge.response;

import com.example.icebutler_server.fridge.entity.fridge.FridgeUser;
import com.example.icebutler_server.fridge.entity.multiFridge.MultiFridgeUser;
import com.example.icebutler_server.fridge.exception.FridgeUserNotFoundException;
import com.example.icebutler_server.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetFridgeMainRes {
  List<FridgeRes> fridgeList;
  List<MultiFridgeRes> multiFridgeResList;

  //  public static GetFridgeMainRes toDto(List<FridgeUser> fridgeUsers, List<List<User>> userListInFridgeUser, List<MultiFridgeUser> multiFridgeUsers, List<List<User>> userListInMultiFridgeUser) {
//    final int[] idx = {0, 0};
//    GetFridgeMainRes getFridgeMainRes = new GetFridgeMainRes();
//    getFridgeMainRes.fridgeList = fridgeUsers.stream().map(ff -> FridgeRes.toDto(ff.getFridge(), ff.getUser(), userListInFridgeUser, idx[0]++)).collect(Collectors.toList());
//    getFridgeMainRes.multiFridgeResList = multiFridgeUsers.stream().map(ff -> MultiFridgeRes.toDto(ff.getMultiFridge(), ff.getUser(), userListInMultiFridgeUser, idx[1]++)).collect(Collectors.toList());
//    return getFridgeMainRes;
//  }

  public static GetFridgeMainRes toDto(List<List<FridgeUser>> fridgeUserListList, List<List<MultiFridgeUser>> multiFridgeUserListList, Long userIdx) {
    GetFridgeMainRes getFridgeMainRes = new GetFridgeMainRes();

    List<FridgeUser> fridgeUsers = fridgeUserListList.stream().map(m -> m.stream().filter(f -> f.getUser().getUserIdx().equals(userIdx)).findAny().orElseThrow(FridgeUserNotFoundException::new)).collect(Collectors.toList());
    getFridgeMainRes.fridgeList = fridgeUsers.stream().map(m -> FridgeRes.toDto(m.getFridge(), fridgeUserListList)).collect(Collectors.toList());

    List<MultiFridgeUser> multiFridgeUsers = multiFridgeUserListList.stream().map(m -> m.stream().filter(f -> f.getUser().getUserIdx().equals(userIdx)).findAny().orElseThrow(FridgeUserNotFoundException::new)).collect(Collectors.toList());
    getFridgeMainRes.multiFridgeResList = multiFridgeUsers.stream().map(m -> MultiFridgeRes.toDto(m.getMultiFridge(), multiFridgeUserListList)).collect(Collectors.toList());

    return getFridgeMainRes;
  }
}
