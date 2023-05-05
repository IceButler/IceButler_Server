package com.example.icebutler_server.user.entity;

import com.example.icebutler_server.global.entity.BaseEntity;
import com.example.icebutler_server.global.entityListener.UserEntityListener;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;

@NoArgsConstructor(access= AccessLevel.PROTECTED)
@Getter
@Entity
@SQLDelete(sql = "UPDATE user SET is_enable = false, update_at = current_timestamp WHERE user_idx = ?")
@EntityListeners(UserEntityListener.class)
public class User extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Long userIdx;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String nickname;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Provider provider;

    private String profileImgKey;

    private Boolean loginStatus;

    @Builder
    public User(Provider provider, String email, String nickname, String profileImgKey) {
        this.provider = provider;
        this.email = email;
        this.nickname = nickname;
        this.profileImgKey = profileImgKey;
    }

    public void login() {
        this.loginStatus = true;
    }

    public void logout() {
        this.loginStatus = false;
    }

    public void modifyProfile(String nickname, String profileImgKey) {
        this.nickname = nickname;
        this.profileImgKey = profileImgKey;
    }
}
