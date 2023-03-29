package com.example.icebutler_server.fridge.entity;

import com.example.icebutler_server.global.entity.BaseEntity;
import com.example.icebutler_server.user.entity.User;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.ALL;

@Getter
@Entity
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class Fridge extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Long fridgeIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="userIdx")
    private User owner;

    private String fridgeName;

    private String fridgeComment;

    @OneToMany(mappedBy="fridge", cascade=ALL)
    private List<FridgeUser> fridgeUsers = new ArrayList<>();


}
