package com.example.icebutler_server.fridge.entity;

import com.example.icebutler_server.fridge.dto.request.AddFridgeReq;
import com.example.icebutler_server.global.entity.BaseEntity;
import com.example.icebutler_server.global.entityListener.FridgeEntityListener;
import lombok.*;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;

@Builder
@AllArgsConstructor
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

    public void edit(String fridgeName, String fridgeComment) {
        this.fridgeName = fridgeName;
        this.fridgeComment = fridgeComment;
    }

    public static Fridge toEntity(AddFridgeReq addFridgeReq) {
        return Fridge.builder()
                .fridgeName(addFridgeReq.getFridgeName())
                .fridgeComment(addFridgeReq.getFridgeComment())
                .build();
    }
}
