package com.example.icebutler_server.user.service;

import com.example.icebutler_server.global.dto.response.ResponseCustom;

public interface UserService {
    Boolean deleteUser(Long userId, String reason);

    Boolean logout(Long userId);
}
