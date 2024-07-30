package com.example.icebutler_server.fridge.entity;

import com.example.icebutler_server.global.entity.BaseEntity;
import com.example.icebutler_server.global.entity.FridgeRole;
import com.example.icebutler_server.global.entityListener.FridgeUserEntityListener;
import com.example.icebutler_server.user.entity.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;

@NoArgsConstructor(access= AccessLevel.PROTECTED)
@Getter
@Entity
@SQLDelete(sql = "UPDATE fridge_user SET is_enable = false, updated_at = current_timestamp WHERE id = ?")
@EntityListeners(FridgeUserEntityListener.class)
public class FridgeUser extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="fridge_id")
    private Fridge fridge;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FridgeRole role;

    @Builder
    public FridgeUser(User user, Fridge fridge, FridgeRole role) {
        this.user = user;
        this.fridge = fridge;
        this.role = role;
    }

    public static FridgeUser toEntity(User user, Fridge fridge, FridgeRole fridgeRole) {
        return FridgeUser.builder()
                .fridge(fridge)
                .user(user)
                .role(fridgeRole)
                .build();
    }

    public void changeRoleToOwner(){
        this.role = FridgeRole.OWNER;
    }

    public void changeRoleToMember(){
        this.role = FridgeRole.MEMBER;
    }

    public void remove() {
        this.setIsEnable(false);
    }
}
