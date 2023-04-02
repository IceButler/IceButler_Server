package com.example.icebutler_server.fridge.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.ALL;
import com.example.icebutler_server.user.entity.User;
@NoArgsConstructor(access= AccessLevel.PROTECTED)
@Getter
@Entity
public class FridgeFood extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private int fridgeFoodIdx;
    private LocalDateTime shelfLife;
    private TextComponent memo;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="userIdx")
    private User foodOwner;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="fridgeIdx")
    private Fridge fridge;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="foodIdx")
    private Food food;
    @OneToMany(mappedBy="fridgeFood", cascade=ALL)
    private List<FridgeFoodImg> fridgeFoodImgs=new ArrayList<>();
}
