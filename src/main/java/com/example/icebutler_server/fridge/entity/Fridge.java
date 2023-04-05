package com.example.icebutler_server.fridge.entity;

import com.example.icebutler_server.user.entity.User;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.ALL;

@NoArgsConstructor(access= AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Entity
@Builder
public class Fridge extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Long fridgeIdx;
    private String fridgeName;
    private String fridgeComment;

    @OneToOne
    @JoinColumn(name = "cartIdx")
    private Cart cart;

    // todo User와 직접적인 연관관계 필요한지 확인 필요, FridgeUser role으로 대체 가능한지?
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="userIdx")
    private User owner;

    @OneToMany(fetch = FetchType.LAZY, mappedBy="fridge", cascade=ALL)
    private List<FridgeUser> fridgeUsers = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy="fridge", cascade=ALL)
    private List<FridgeFood> fridgeFoods = new ArrayList<>();

    public void updateOwner(User updateFridgeOwner) {
        this.owner = updateFridgeOwner;
    }

    public void updateMembers(List<FridgeUser> updateMembers) {
        this.fridgeUsers = updateMembers;
    }

    public void updateNameAndComment(Fridge toUpdateEntity) {
        this.fridgeName = toUpdateEntity.getFridgeName();
        this.fridgeComment = toUpdateEntity.getFridgeComment();
    }

    public void updateIsEnable(boolean b) {
        this.setIsEnable(b);
    }
}
