package com.example.icebutler_server.global.resolver;

import lombok.Builder;
import lombok.Data;

@Data
public class LoginStatus {
    private Boolean isLogin;
    private Long userId;
    @Builder
    public LoginStatus(Boolean isLogin, Long userId) {
        this.isLogin = isLogin;
        this.userId = userId;
    }
    public static LoginStatus getNotLoginStatus() {
        return new LoginStatus(false, null);
    }
}
