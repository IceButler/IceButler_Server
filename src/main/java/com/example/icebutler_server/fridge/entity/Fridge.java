package com.example.icebutler_server.fridge.entity;

import com.example.icebutler_server.fridge.dto.request.FridgeRegisterReq;
import com.example.icebutler_server.global.entity.BaseEntity;
import com.example.icebutler_server.global.entityListener.FridgeEntityListener;
import org.hibernate.annotations.SQLDelete;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@SQLDelete(sql = "UPDATE fridge SET is_enable = false, updated_at = current_timestamp WHERE id = ?")
@EntityListeners(FridgeEntityListener.class)
public class Fridge extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    private String fridgeName;

    private String fridgeComment;

    @Builder
    public Fridge(
            String fridgeName,
            String fridgeComment) {
        this.fridgeName = fridgeName;
        this.fridgeComment = fridgeComment;
    }

    public void updateBasicFridgeInfo(String fridgeName, String fridgeComment) {
        this.fridgeName = fridgeName;
        this.fridgeComment = fridgeComment;
    }

    public void remove() {
        this.setIsEnable(false);
    }

    public static Fridge toEntity(FridgeRegisterReq fridgeRegisterReq) {
        return Fridge.builder()
                .fridgeName(fridgeRegisterReq.getFridgeName())
                .fridgeComment(fridgeRegisterReq.getFridgeComment())
                .build();
    }
}
