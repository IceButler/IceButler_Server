package com.example.icebutler_server.fridge.dto.fridge.response;

import com.example.icebutler_server.fridge.entity.fridge.FridgeUser;
import com.example.icebutler_server.fridge.entity.multiFridge.MultiFridgeUser;
import com.example.icebutler_server.fridge.exception.FridgeUserNotFoundException;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(name = "GetFridgesMainRes", description = "마이냉장고 정보")
public class GetFridgesMainRes {
  @Schema(name = "fridgeList", description = "가정용 냉장고 정보")
  List<FridgeRes> fridgeList;
  @Schema(name = "multiFridgeResList", description = "공용 냉장고 정보")
  List<MultiFridgeRes> multiFridgeResList;

  public static GetFridgesMainRes toDto(List<List<FridgeUser>> fridgeUserListList, List<List<MultiFridgeUser>> multiFridgeUserListList, Long userIdx) {
    GetFridgesMainRes getFridgesMainRes = new GetFridgesMainRes();

    List<FridgeUser> fridgeUsers = fridgeUserListList.stream().map(m -> m.stream().filter(f -> f.getUser().getId().equals(userIdx)).findAny().orElseThrow(FridgeUserNotFoundException::new)).collect(Collectors.toList());
    getFridgesMainRes.fridgeList = fridgeUsers.stream().map(m -> FridgeRes.toDto(m.getFridge(), fridgeUserListList)).collect(Collectors.toList());

    List<MultiFridgeUser> multiFridgeUsers = multiFridgeUserListList.stream().map(m -> m.stream().filter(f -> f.getUser().getId().equals(userIdx)).findAny().orElseThrow(FridgeUserNotFoundException::new)).collect(Collectors.toList());
    getFridgesMainRes.multiFridgeResList = multiFridgeUsers.stream().map(m -> MultiFridgeRes.toDto(m.getMultiFridge(), multiFridgeUserListList)).collect(Collectors.toList());

    return getFridgesMainRes;
  }
}
