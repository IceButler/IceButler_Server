package com.example.icebutler_server.fridge.entity.fridge;

import com.example.icebutler_server.global.entity.BaseEntity;
import com.example.icebutler_server.global.entity.FridgeRole;
import com.example.icebutler_server.user.entity.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access= AccessLevel.PROTECTED)
@Getter
@Entity
public class FridgeUser extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Long fridgeUserIdx;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="userIdx")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="fridgeIdx")
    private Fridge fridge;

    @Enumerated(EnumType.STRING)
    private FridgeRole role;

    @Builder
    public FridgeUser(User user, Fridge fridge, FridgeRole role) {
        this.user = user;
        this.fridge = fridge;
        this.role = role;
    }

    public void changeFridgeOwner(User user){
        this.role = FridgeRole.OWNER;
    }
    public void changeFridgeMember(User user){
        this.role = FridgeRole.MEMBER;
    }

    public void remove(Boolean status) {
        this.setIsEnable(status);
    }
}
