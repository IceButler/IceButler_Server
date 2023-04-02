package com.example.icebutler_server.user.entity;

import com.example.icebutler_server.fridge.entity.Cart;
import com.example.icebutler_server.fridge.entity.FridgeUser;
import com.example.icebutler_server.fridge.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.ALL;

@NoArgsConstructor(access= AccessLevel.PROTECTED)
@Getter
@Entity
public class User extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Long userIdx;
    private String email;
    private String nickname;
    private String oauthProvider;
    private String profileImage;
    private Boolean loginStatus;
    @OneToMany(mappedBy="user", cascade=ALL)
    private List<FridgeUser> fridgeUsers = new ArrayList<>();
    @OneToOne(cascade = ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "cartIdx")
    private Cart cart;

    public void addCart(Cart cart) {
        this.cart = cart;
    }
}
