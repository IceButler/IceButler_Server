package com.example.icebutler_server.fridge.entity;

import com.example.icebutler_server.global.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@NoArgsConstructor(access= AccessLevel.PROTECTED)
@Getter
@Entity
public class MultiFridgeFoodImg extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(nullable = false)
  private int fridgeFoodImgIdx;
  private String fridgeFoodImg;
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name="multiFridgeFoodIdx")
  private MultiFridgeFood multiFridgeFood;
}
