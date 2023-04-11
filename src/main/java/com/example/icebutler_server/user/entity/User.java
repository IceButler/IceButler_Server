package com.example.icebutler_server.user.entity;

import com.example.icebutler_server.global.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access= AccessLevel.PROTECTED)
@Getter
@Entity
public class User extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Long userIdx;
    private String email;
    private String nickname;
    @Enumerated(EnumType.STRING)
    private Provider provider;
    private String profileImage;
    private Boolean loginStatus;

    @Builder
    public User(Provider provider, String email) {
        this.provider = provider;
        this.email = email;
    }

    public void login() {
        this.loginStatus = true;
    }

    public void modifyNickname(String nickName) {
        this.nickname = nickName;
    }

    public void modifyProfileImg(String profileImage) {
        this.profileImage = profileImage;
    }
}
