package com.example.icebutler_server.fridge.dto.response;

import com.example.icebutler_server.fridge.entity.Fridge;
import com.example.icebutler_server.fridge.entity.FridgeUser;
import com.example.icebutler_server.fridge.entity.fridge.Fridge;
import com.example.icebutler_server.fridge.entity.fridge.FridgeUser;
import com.example.icebutler_server.fridge.exception.FridgeUserNotFoundException;
import com.example.icebutler_server.global.entity.FridgeRole;
import com.example.icebutler_server.user.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "FridgeRes", description = "가정용 냉장고 정보")
public class FridgeRes {
  @Schema(name = "fridgeIdx", description = "냉장고 ID")
  private Long fridgeIdx;
  @Schema(name = "fridgeName", description = "냉장고 이름")
  private String fridgeName;
  @Schema(name = "comment", description = "냉장고 설명")
  private String comment;
  @Schema(name = "users", description = "냉장고 유저 정보")
  private List<FridgeUserRes> users;
  @Schema(name = "userCnt", description = "냉장고 유저수")
  private Integer userCnt;

  public static FridgeRes toDto(Fridge fridge, List<List<FridgeUser>> fridgeUserList) {
    List<FridgeUser> fridgeUsers = new ArrayList<>();
    for (List<FridgeUser> fridgeArr : fridgeUserList) {
      for (FridgeUser fridgeUser : fridgeArr) {
        if (fridgeUser.getFridge().getId().equals(fridge.getId())) fridgeUsers.add(fridgeUser);
      }
    }

    FridgeRes fridgeRes = new FridgeRes();
    fridgeRes.fridgeIdx = fridge.getId();
    fridgeRes.fridgeName = fridge.getFridgeName();
    fridgeRes.comment = fridge.getFridgeComment();
    fridgeRes.users = fridgeUsers.stream().map(m -> FridgeUserRes.toDto(m.getUser(), m.getRole())).collect(Collectors.toList());
    fridgeRes.userCnt = fridgeUsers.size();
    return fridgeRes;
  }
}