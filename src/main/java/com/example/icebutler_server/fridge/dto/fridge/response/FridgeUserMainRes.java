package com.example.icebutler_server.fridge.dto.fridge.response;

import com.example.icebutler_server.fridge.entity.fridge.FridgeUser;
import com.example.icebutler_server.fridge.entity.multiFridge.MultiFridgeUser;
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
@Schema(name = "FridgeUserMainRes", description = "냉장고 멤버 정보")
public class FridgeUserMainRes {
    @Schema(name = "fridgeUsers", description = "냉장고 멤버 정보")
    List<FridgeUsersRes> fridgeUsers;

    public static FridgeUserMainRes doDto(List<FridgeUser> fridgeUsers){
        FridgeUserMainRes fridgeUserMainRes=new FridgeUserMainRes();
        fridgeUserMainRes.fridgeUsers=fridgeUsers.stream().map(cf->FridgeUsersRes.toDto(cf.getUser())).collect(Collectors.toList());
        return fridgeUserMainRes;
    }

    public static FridgeUserMainRes doMultiDto(List<MultiFridgeUser> fridgeUsers){
        FridgeUserMainRes fridgeUserMainRes=new FridgeUserMainRes();
        fridgeUserMainRes.fridgeUsers=fridgeUsers.stream().map(cf->FridgeUsersRes.toDto(cf.getUser())).collect(Collectors.toList());
        return fridgeUserMainRes;
    }

}
