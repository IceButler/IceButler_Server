package com.example.icebutler_server.user.exception;

public class UserNicknameNotFoundException extends RuntimeException{
    public UserNicknameNotFoundException(){
        super("요청한 닉네임을 가진 유저를 찾을 수 없습니다.");
    }

}
