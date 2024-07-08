package com.example.icebutler_server.food.entity;

import com.example.icebutler_server.admin.dto.request.ModifyFoodRequest;
import com.example.icebutler_server.cart.dto.request.AddFoodRequest;
import com.example.icebutler_server.food.dto.request.FoodReq;
import com.example.icebutler_server.fridge.dto.request.FridgeFoodReq;
import com.example.icebutler_server.global.entity.BaseEntity;
import com.example.icebutler_server.global.entityListener.FoodEntityListener;
import com.example.icebutler_server.global.util.AwsS3ImageUrlUtil;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

import static com.example.icebutler_server.global.util.Constant.Food.ICON_EXTENSION;
import static com.example.icebutler_server.global.util.Constant.Food.IMG_FOLDER;

@NoArgsConstructor(access= AccessLevel.PROTECTED)
@Getter
@Entity
@SQLDelete(sql = "UPDATE food SET is_enable = false, updated_at = current_timestamp WHERE id = ?")
@EntityListeners(FoodEntityListener.class)
public class Food extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Long id;
    private String foodName;
    private String foodImgKey;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FoodCategory foodCategory;

//    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Type(type = "uuid-char")
    private UUID uuid;

    @Builder
    public Food(String foodName, String foodImgKey, FoodCategory foodCategory, UUID uuid) {
        this.foodName = foodName;
        this.foodImgKey = foodImgKey;
        this.foodCategory = foodCategory;
        this.uuid = uuid;
    }

    public void toUpdateInfo(String foodName, FoodCategory foodCategory, String foodImgKey) {
        this.foodName = foodName;
        this.foodCategory = foodCategory;
        this.foodImgKey = foodImgKey;
    }

    public void toUpdateImgKey(String foodImgKey) {
        this.foodImgKey = foodImgKey;
    }

    public void toUpdateName(String foodName) {
        this.foodName = foodName;
    }

    public void toUpdateCategory(FoodCategory foodCategory) {
        this.foodCategory = foodCategory;
    }

    public static Food toEntity(FoodReq request) {
        return Food.builder()
                .foodName(request.getFoodName())
                .foodImgKey(request.getFoodImgKey())
                // TODO 큐에서 전달 받을 때 category value 값을 받아서 우선 변경, 해당 부분 통일 필요
                .foodCategory(FoodCategory.valueOf(request.getFoodCategory()))
                .uuid(request.getUuid())
                .build();
    }

    public static Food toEntity(FridgeFoodReq request) {
        FoodCategory foodCategory = FoodCategory.getFoodCategoryByName(request.getFoodCategory());
        String foodImageKey = IMG_FOLDER + foodCategory.toString() + ICON_EXTENSION;
        return Food.builder()
                .foodName(request.getFoodName())
                .foodCategory(FoodCategory.getFoodCategoryByName(request.getFoodCategory()))
                .foodImgKey(foodImageKey)
                .uuid(UUID.randomUUID())
                .build();
    }

    public static Food toEntity(AddFoodRequest request) {
        FoodCategory foodCategory = FoodCategory.getFoodCategoryByName(request.getFoodCategory());
        String foodImageKey = IMG_FOLDER + foodCategory.toString() + ICON_EXTENSION;
        return Food.builder()
                .foodName(request.getFoodName())
                .foodImgKey(foodImageKey)
                .foodCategory(foodCategory)
                .uuid(UUID.randomUUID())
                .build();
    }
}

