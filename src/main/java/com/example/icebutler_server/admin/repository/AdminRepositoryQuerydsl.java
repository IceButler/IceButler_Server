package com.example.icebutler_server.admin.repository;

import com.example.icebutler_server.admin.dto.response.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AdminRepositoryQuerydsl {
    Page<UserResponse> findAllByNicknameAndActive(Pageable pageable, String nickname, boolean active);
}
