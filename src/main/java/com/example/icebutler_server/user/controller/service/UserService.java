package com.example.icebutler_server.user.controller.service;


import com.example.icebutler_server.user.dto.GetProfileRes;
import com.example.icebutler_server.user.dto.UserSearchRes;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

public interface UserService {
    Boolean deleteUser(Long userIdx);

    Boolean logout(Long userIdx);

    GetProfileRes getProfile(Long userIdx);

    UserSearchRes searchUsers(Long userIdx );
}