package com.example.icebutler_server.fridge.dto.response;

import com.example.icebutler_server.fridge.entity.FridgeUser;
import com.example.icebutler_server.global.exception.BaseException;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.icebutler_server.global.exception.ReturnCode.NOT_FOUND_FRIDGE_USER;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "GetFridgesMainRes", description = "마이냉장고 정보")
public class GetFridgesMainRes {
  @Schema(name = "fridgeList", description = "가정용 냉장고 정보")
  List<FridgeRes> fridgeList;

  public static GetFridgesMainRes toDto(List<List<FridgeUser>> fridgeUserListList, Long userIdx) {
    GetFridgesMainRes getFridgesMainRes = new GetFridgesMainRes();

    List<FridgeUser> fridgeUsers = fridgeUserListList.stream().map(m -> m.stream().filter(f -> f.getUser().getId().equals(userIdx)).findAny().orElseThrow(() -> new BaseException(NOT_FOUND_FRIDGE_USER))).collect(Collectors.toList());
    getFridgesMainRes.fridgeList = fridgeUsers.stream().map(m -> FridgeRes.toDto(m.getFridge(), fridgeUserListList)).collect(Collectors.toList());

    return getFridgesMainRes;
  }
}
