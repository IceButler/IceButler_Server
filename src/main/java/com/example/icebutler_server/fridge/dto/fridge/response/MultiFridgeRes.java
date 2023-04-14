package com.example.icebutler_server.fridge.dto.fridge.response;

import com.example.icebutler_server.fridge.entity.fridge.Fridge;
import com.example.icebutler_server.fridge.entity.multiFridge.MultiFridge;
import com.example.icebutler_server.fridge.entity.multiFridge.MultiFridgeUser;
import com.example.icebutler_server.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MultiFridgeRes {
    private String ownerNickname;
    private String multiFridgeName;
    private String comment;
    private List<MultiFridgeUser> users;

    public static MultiFridgeRes toDto(MultiFridge fridge, User user){
        MultiFridgeRes fridgeRes=new MultiFridgeRes();
        fridgeRes.ownerNickname=user.getNickname();
        fridgeRes.multiFridgeName=fridge.getFridgeName();
        fridgeRes.comment=fridge.getFridgeComment();
        return fridgeRes;
    }
}
