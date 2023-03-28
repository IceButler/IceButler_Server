package com.example.icebutler_server.fridge.entity;

import com.example.icebutler_server.global.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access= AccessLevel.PROTECTED)
@Getter
@Entity
public class FridgeFoodImg extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private int fridgeFoddImgIdx;
    private String fridgeFoodImg;
    private int fridgeFoodIdx;
    private boolean status;

}
