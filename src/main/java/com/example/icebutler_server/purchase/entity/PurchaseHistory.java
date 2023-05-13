package com.example.icebutler_server.purchase.entity;

import com.example.icebutler_server.global.entity.BaseEntity;
import com.example.icebutler_server.purchase.dto.response.InAppRes;
import com.example.icebutler_server.user.entity.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;
import java.util.Date;

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

    private Subscription subscription;

    private String productId;
    private String transactionId;
    private Date purchaseDate;
    private boolean isTrialPeriod;

    @Builder
    public PurchaseHistory(User user, Subscription subscription, String productId, String transactionId, Date purchaseDate, boolean isTrialPeriod) {
        this.user = user;
        this.subscription = subscription;
        this.productId = productId;
        this.transactionId = transactionId;
        this.purchaseDate = purchaseDate;
        this.isTrialPeriod = isTrialPeriod;
    }

    public static PurchaseHistory toEntity(User user, Subscription subscription, InAppRes inAppRes) {
        return PurchaseHistory.builder().user(user)
                .subscription(subscription)
                .productId(inAppRes.getProductId())
                .transactionId(inAppRes.getTransactionId())
                .purchaseDate(inAppRes.getPurchaseDate())
                .isTrialPeriod(inAppRes.isTrialPeriod())
                .build();
    }
}
