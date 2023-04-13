package com.example.icebutler_server.user.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class IsEnableRes {
    private Boolean isEnable;

    @Builder
    public IsEnableRes(Boolean isEnable){
        this.isEnable=isEnable;
    }

}
