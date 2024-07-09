package com.example.icebutler_server.fridge.dto.assembler;

import com.example.icebutler_server.fridge.dto.request.FridgeRegisterReq;
import com.example.icebutler_server.fridge.dto.response.UpdateMembersRes;
import com.example.icebutler_server.fridge.entity.Fridge;
import com.example.icebutler_server.fridge.entity.FridgeFood;
import com.example.icebutler_server.fridge.entity.FridgeUser;
import com.example.icebutler_server.global.entity.FridgeRole;
import com.example.icebutler_server.global.exception.BaseException;
import com.example.icebutler_server.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static com.example.icebutler_server.global.exception.ReturnCode.NO_PERMISSION;
import static com.example.icebutler_server.global.exception.ReturnCode.STILL_MEMBER_EXIST;

@Component
@RequiredArgsConstructor
public class FridgeAssembler {

  public Fridge toEntity(FridgeRegisterReq createFridgeReq) {
    return Fridge.builder()
            .fridgeName(createFridgeReq.getFridgeName())
            .fridgeComment(createFridgeReq.getFridgeComment())
            .build();
  }

  public void toUpdateFridgeOwner(FridgeUser owner, FridgeUser newOwner) {
    owner.changeFridgeMember(owner.getUser());
    newOwner.changeFridgeOwner(newOwner.getUser());
  }

  public UpdateMembersRes toUpdateFridgeMembers(List<User> newMembers, List<FridgeUser> fridgeUsers) {
    for (FridgeUser member : fridgeUsers) {
      member.setIsEnable(false);
    }
    List<FridgeUser> checkNewMember = new ArrayList<>();
    List<FridgeUser> withDrawMember = new ArrayList<>();

    for (User user : newMembers) {
      boolean hasMember = false;

      for (FridgeUser members : fridgeUsers) {
        if (user.equals(members.getUser())) {
          members.setIsEnable(true);
          hasMember = true;
        }
        if(members.getRole().equals(FridgeRole.OWNER)){
          members.setIsEnable(true);
        }
      }
      if (!hasMember) {
        checkNewMember.add(FridgeUser.builder()
                .user(user)
                .role(FridgeRole.MEMBER)
                .fridge(fridgeUsers.get(0).getFridge())
                .build());
      }
    }
    for (FridgeUser f : fridgeUsers) {
      if(!f.getIsEnable()) withDrawMember.add(f);
    }
    return UpdateMembersRes.toDto(withDrawMember, checkNewMember);
  }

  public void removeFridge(FridgeUser owner, Fridge fridge, List<FridgeUser> fridgeUsers, List<FridgeFood> fridgeFoods) {
    if (owner.getRole() != FridgeRole.OWNER) throw new BaseException(NO_PERMISSION);
    if(fridgeUsers.size() > 1) throw new BaseException(STILL_MEMBER_EXIST);

    fridgeUsers.forEach(FridgeUser::remove);
//    fridgeFoods.forEach(FridgeFood::remove);
    fridge.remove();
  }
}
