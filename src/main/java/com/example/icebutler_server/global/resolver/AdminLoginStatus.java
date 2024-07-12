package com.example.icebutler_server.global.resolver;

import lombok.Builder;
import lombok.Data;

@Data
public class AdminLoginStatus {
    private Boolean isLogin;
    private Long adminId;
    @Builder
    public AdminLoginStatus(Boolean isLogin, Long adminId) {
        this.isLogin = isLogin;
        this.adminId = adminId;
    }
    public static AdminLoginStatus getNotAdminLoginStatus() {
        return new AdminLoginStatus(false, null);
    }
}
