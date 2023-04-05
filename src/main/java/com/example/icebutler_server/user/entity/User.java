package com.example.icebutler_server.user.entity;

import com.example.icebutler_server.fridge.entity.Cart;
import com.example.icebutler_server.fridge.entity.FridgeUser;
import com.example.icebutler_server.fridge.entity.BaseEntity;
import com.example.icebutler_server.user.entity.vo.UserType;

import com.example.icebutler_server.fridge.entity.*;
import com.example.icebutler_server.recipe.entity.Recipe;
import com.example.icebutler_server.recipe.entity.RecipeLike;
import com.example.icebutler_server.recipe.entity.RecipeReport;
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

    @OneToMany(mappedBy="owner", cascade=ALL)
    private List<FridgeUser> fridgeUsers = new ArrayList<>();


    @Enumerated(EnumType.STRING)
    private UserType userType;


    public void setUserType(UserType userType){this.userType = userType;}

    @OneToMany(mappedBy="owner", cascade=ALL)
    private List<MultiFridge> multiFridges = new ArrayList<>();

    @OneToMany(mappedBy="user", cascade=ALL)
    private List<Recipe> recipes = new ArrayList<>();

    @OneToMany(mappedBy="user", cascade=ALL)
    private List<RecipeLike> recipeLikes = new ArrayList<>();

    @OneToMany(mappedBy="user", cascade=ALL)
    private List<RecipeReport> recipeReports = new ArrayList<>();

}
