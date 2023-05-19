package com.example.icebutler_server.global.feign.dto;

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
}
