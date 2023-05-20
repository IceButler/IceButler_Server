package com.example.icebutler_server.admin.dto.response;

import com.example.icebutler_server.admin.entity.Admin;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AdminResponse {
    private String email;

    public static AdminResponse toDto(Admin saveAdmin) {
        return new AdminResponse(saveAdmin.getEmail());
    }

    public AdminResponse(String email){
        this.email = email;
    }
}
