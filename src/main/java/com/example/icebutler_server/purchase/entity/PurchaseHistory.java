package com.example.icebutler_server.purchase.entity;

import com.example.icebutler_server.global.entity.BaseEntity;
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
@SQLDelete(sql = "UPDATE purchase_history SET is_enable = false, update_at = current_timestamp WHERE purchase_history_idx = ?")
public class PurchaseHistory extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Long purchaseHistoryIdx;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userIdx")
    private User user;

    @Builder
    public PurchaseHistory(User user) {
        this.user = user;
    }
}
