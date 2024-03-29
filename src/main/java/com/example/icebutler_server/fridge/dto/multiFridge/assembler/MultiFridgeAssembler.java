package com.example.icebutler_server.fridge.dto.multiFridge.assembler;

import com.example.icebutler_server.cart.entity.multiCart.MultiCart;
import com.example.icebutler_server.fridge.dto.fridge.request.FridgeModifyReq;
import com.example.icebutler_server.fridge.dto.fridge.request.FridgeRegisterReq;
import com.example.icebutler_server.fridge.dto.fridge.response.UpdateMembersRes;
import com.example.icebutler_server.fridge.dto.fridge.response.UpdateMultiMemberRes;
import com.example.icebutler_server.fridge.entity.fridge.FridgeUser;
import com.example.icebutler_server.fridge.entity.multiFridge.MultiFridge;
import com.example.icebutler_server.fridge.entity.multiFridge.MultiFridgeUser;
import com.example.icebutler_server.global.entity.FridgeRole;
import com.example.icebutler_server.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class MultiFridgeAssembler {
    public void toUpdateBasicMultiFridgeInfo(MultiFridge fridge, FridgeModifyReq updateFridgeReq) {
        fridge.updateBasicFridgeInfo(updateFridgeReq.getFridgeName(), updateFridgeReq.getFridgeComment());
    }

    public void toUpdateFridgeOwner(MultiFridgeUser owner, MultiFridgeUser newOwner) {
        owner.changeFridgeMember(owner.getUser());
        newOwner.changeFridgeOwner(newOwner.getUser());
    }

    public UpdateMultiMemberRes toUpdateFridgeMembers(List<User> newMembers, List<MultiFridgeUser> multiFridgeUsers) {
        for(MultiFridgeUser member: multiFridgeUsers){
            member.setIsEnable(false);
        }
        List<MultiFridgeUser> checkNewMember = new ArrayList<>();
        List<MultiFridgeUser> withDrawMember = new ArrayList<>();

        for(User user: newMembers){
            boolean hasMember = false;

            for(MultiFridgeUser members : multiFridgeUsers){
                if(user.equals(members.getUser())) {
                    members.setIsEnable(true);
                    hasMember = true;
                }

                if(members.getRole().equals(FridgeRole.OWNER)){
                    members.setIsEnable(true);
                }
            }
            if(!hasMember) {
                checkNewMember.add(MultiFridgeUser.builder()
                        .user(user)
                        .role(FridgeRole.MEMBER)
                        .multiFridge(multiFridgeUsers.get(0).getMultiFridge())
                        .build());
            }
        }

        for (MultiFridgeUser f : multiFridgeUsers) {
            if(!f.getIsEnable()) withDrawMember.add(f);
        }
        return UpdateMultiMemberRes.toDto(withDrawMember, checkNewMember);
    }

    public MultiFridge toEntity(FridgeRegisterReq registerFridgeReq) {
        return MultiFridge.builder()
                .fridgeName(registerFridgeReq.getFridgeName())
                .fridgeComment(registerFridgeReq.getFridgeComment())
                .build();
    }

    public List<MultiCart> multiCartToEntity(List<MultiFridgeUser> multiFridgeUsers) {
        return multiFridgeUsers.stream()
                .map(m -> MultiCart.builder().multiFridgeUser(m).build())
                .collect(Collectors.toList());
    }
}
