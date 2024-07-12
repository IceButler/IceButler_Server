package com.example.icebutler_server.global.feign.dto;

import com.example.icebutler_server.admin.entity.Admin;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class AdminReq {
    private Long adminId;
    private String email;

    @Builder
    public AdminReq(Long adminId, String email) {
        this.adminId = adminId;
        this.email = email;
    }

    public static AdminReq toDto(Admin admin){
        AdminReq adminReq = new AdminReq();
        adminReq.adminId = admin.getId();
        adminReq.email = admin.getEmail();
        return adminReq;
    }
}
