package com.example.icebutler_server.admin.dto.request;

import com.example.icebutler_server.admin.entity.Admin;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class JoinRequest {
    private String email;
    private String password;

    public Admin toAdmin(String password) {
        return new Admin(this.email, password);
    }
}
