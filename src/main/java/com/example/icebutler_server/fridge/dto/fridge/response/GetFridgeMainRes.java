package com.example.icebutler_server.fridge.dto.fridge.response;

import com.example.icebutler_server.fridge.entity.fridge.FridgeUser;
import com.example.icebutler_server.fridge.entity.multiFridge.MultiFridgeUser;
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
//    List<MultiFridgeRes> multiFridgeResList;


    public static GetFridgeMainRes toDto(List<FridgeUser> fridgeUsers
//                                         List<MultiFridgeUser> multiFridgeUsers
                                         ){
        GetFridgeMainRes getFridgeMainRes=new GetFridgeMainRes();
        getFridgeMainRes.fridgeList=fridgeUsers.stream()
                .map(cf ->FridgeRes.toDto(cf.getFridge(),cf.getUser())).collect(Collectors.toList());
//        getFridgeMainRes.multiFridgeResList=multiFridgeUsers.stream().map(ff->MultiFridgeRes.toDto(ff.getMultiFridge(),ff.getUser())).collect(Collectors.toList());
        return getFridgeMainRes;
    }
}
