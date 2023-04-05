package com.example.icebutler_server.user.service;

import com.example.icebutler_server.user.dto.response.GetProfileRes;
import com.example.icebutler_server.user.dto.response.UserSearchRes;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

public interface UserService {
    Boolean deleteUser(Long userIdx, String reason);

    Boolean logout(Long userIdx);

    GetProfileRes getProfile(Long userIdx);

    UserSearchRes searchUsers(Long userIdx );
}
