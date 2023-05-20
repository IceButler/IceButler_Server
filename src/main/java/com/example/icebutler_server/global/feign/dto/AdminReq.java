package com.example.icebutler_server.global.feign.dto;

import com.example.icebutler_server.admin.entity.Admin;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class AdminReq {
    private Long adminIdx;
    private String email;

    @Builder
    public AdminReq(Long adminIdx, String email) {
        this.adminIdx = adminIdx;
        this.email = email;
    }

    public static AdminReq toDto(Admin admin){
        AdminReq adminReq = new AdminReq();
        adminReq.adminIdx = admin.getAdminIdx();
        adminReq.email = admin.getEmail();
        return adminReq;
    }
}
