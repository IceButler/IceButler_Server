package com.example.icebutler_server.fridge.entity.multiFridge;

import com.example.icebutler_server.global.entity.BaseEntity;
import com.example.icebutler_server.global.entity.FridgeRole;
import com.example.icebutler_server.global.entityListener.MultiFridgeUserEntityListener;
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
@SQLDelete(sql = "UPDATE multi_fridge_user SET is_enable = false, update_at = current_timestamp WHERE multi_fridge_user_idx = ?")
@EntityListeners(MultiFridgeUserEntityListener.class)
public class MultiFridgeUser extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(nullable = false)
  private Long multiFridgeUserIdx;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name="userIdx")
  private User user;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name="multiFridgeIdx")
  private MultiFridge multiFridge;

  @Enumerated(EnumType.STRING)
  private FridgeRole role;

  @Builder
  public MultiFridgeUser(User user, MultiFridge multiFridge, FridgeRole role) {
    this.user = user;
    this.multiFridge = multiFridge;
    this.role = role;
  }

  public void changeFridgeOwner(User user){
    this.role = FridgeRole.OWNER;
  }
  public void changeFridgeMember(User user){
    this.role = FridgeRole.MEMBER;
  }
}
