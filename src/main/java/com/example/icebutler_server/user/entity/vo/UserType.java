package com.example.icebutler_server.user.entity.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserType {
    ACCESS,
    DELETE,
    STOP;
}
