package com.example.icebutler_server.fridge.controller;

import com.example.icebutler_server.fridge.dto.fridge.request.FridgeModifyReq;
import com.example.icebutler_server.fridge.service.MultiFridgeServiceImpl;
import com.example.icebutler_server.global.dto.response.ResponseCustom;
import com.example.icebutler_server.global.resolver.IsLogin;
import com.example.icebutler_server.global.resolver.LoginStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/multiFridges")
@RestController
@RequiredArgsConstructor
public class MultiFridgeController {
    private final MultiFridgeServiceImpl multiFridgeService;

    /**
     * [patch] 냉장고 수정
     */
    @PatchMapping("/{multiFridgeIdx}")
    @ResponseBody
    public ResponseCustom<?> modifyFridge(@PathVariable(name = "multiFridgeIdx") Long multiFridgeIdx,
                                                 @RequestBody FridgeModifyReq fridgeModifyReq,
                                                 @IsLogin LoginStatus loginStatus) {
        this.multiFridgeService.modifyFridge(multiFridgeIdx, fridgeModifyReq, loginStatus.getUserIdx());
        return ResponseCustom.OK();
    }
}
