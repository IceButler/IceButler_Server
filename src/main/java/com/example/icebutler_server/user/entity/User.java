package com.example.icebutler_server.user.entity;

import com.example.icebutler_server.fridge.entity.fridge.FridgeUser;
import com.example.icebutler_server.global.entity.BaseEntity;
import com.example.icebutler_server.fridge.entity.multiFridge.MultiFridge;
import com.example.icebutler_server.recipe.entity.Recipe;
import com.example.icebutler_server.recipe.entity.RecipeLike;
import com.example.icebutler_server.recipe.entity.RecipeReport;
import lombok.AccessLevel;
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
    private String oauthProvider;
    private String profileImage;
    private Boolean loginStatus;

}
