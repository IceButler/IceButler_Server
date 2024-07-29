package com.example.icebutler_server.fridge.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "냉장고 추가 요청 정보")
public class AddFridgeReq {
  @NotBlank
  @Schema(description = "냉장고 이름", example = "우리집 냉장고")
  private String fridgeName;
  @Schema(description = "냉장고 설명", example = "우리집 냉장고입니다~")
  private String fridgeComment;
  @Schema(description = "냉장고 멤버 ID 리스트", example = "[ 1, 2 ]")
  private List<Long> members;
}
