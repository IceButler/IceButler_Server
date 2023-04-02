package com.example.icebutler_server.fridge.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access= AccessLevel.PROTECTED)
@Getter
@Entity
public class FridgeFoodImg extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private int fridgeFoodImgIdx;
    private String fridgeFoodImg;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="fridgeFoodIdx")
    private FridgeFood fridgeFood;
}
