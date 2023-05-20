package com.example.icebutler_server.admin.entity;

import com.example.icebutler_server.global.entity.BaseEntity;
import com.example.icebutler_server.global.entityListener.UserEntityListener;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access= AccessLevel.PROTECTED)
@Getter
@Entity
@EntityListeners(UserEntityListener.class)
public class Admin extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Long adminIdx;
    @Column(nullable = false)
    private String email;
    private String password;
    private Boolean loginStatus;

    public void login() {
        this.loginStatus = true;
    }

    public Admin(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
