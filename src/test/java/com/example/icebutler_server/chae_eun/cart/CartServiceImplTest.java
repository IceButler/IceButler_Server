package com.example.icebutler_server.chae_eun.cart;

import com.example.icebutler_server.cart.dto.cart.request.AddFoodRequest;
import com.example.icebutler_server.cart.dto.cart.request.AddFoodToCartRequest;
import com.example.icebutler_server.cart.dto.cart.response.CartResponse;
import com.example.icebutler_server.cart.entity.cart.Cart;
import com.example.icebutler_server.cart.entity.cart.CartFood;
import com.example.icebutler_server.cart.exception.CartNotFoundException;
import com.example.icebutler_server.cart.repository.cart.CartFoodRepository;
import com.example.icebutler_server.cart.repository.cart.CartRepository;
import com.example.icebutler_server.cart.service.CartServiceImpl;
import com.example.icebutler_server.food.entity.Food;
import com.example.icebutler_server.food.entity.FoodCategory;
import com.example.icebutler_server.food.repository.FoodRepository;
import com.example.icebutler_server.fridge.entity.fridge.Fridge;
import com.example.icebutler_server.fridge.entity.fridge.FridgeUser;
import com.example.icebutler_server.fridge.exception.FridgeNotFoundException;
import com.example.icebutler_server.fridge.exception.FridgeUserNotFoundException;
import com.example.icebutler_server.fridge.repository.fridge.FridgeRepository;
import com.example.icebutler_server.fridge.repository.fridge.FridgeUserRepository;
import com.example.icebutler_server.global.entity.FridgeRole;
import com.example.icebutler_server.user.entity.Provider;
import com.example.icebutler_server.user.entity.User;
import com.example.icebutler_server.user.exception.UserNotFoundException;
import com.example.icebutler_server.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class CartServiceImplTest {

    @Autowired
    private CartServiceImpl cartService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FridgeRepository fridgeRepository;
    @Autowired
    private FridgeUserRepository fridgeUserRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private FoodRepository foodRepository;
    @Autowired
    private CartFoodRepository cartFoodRepository;

    /**
     * 장바구니 식품 조회
     */
    @DisplayName("냉장고 카트를 불러온다.")
    @Test
    @Transactional
    void getCart() {
        // given
        User user = createUser();
        userRepository.save(user);
        Fridge fridge = createFridge();
        fridgeRepository.save(fridge);
        FridgeUser fridgeUser = createFridgeUser(fridge, user);
        fridgeUserRepository.save(fridgeUser);
        Cart cart = createCart(fridge);
        cartRepository.save(cart);
        Food food = createFood();
        foodRepository.save(food);
        CartFood cartFood = createCartFood(cart, food);
        cartFoodRepository.save(cartFood);

        // when
        Cart cart1 = getCart(user.getUserIdx(), fridge.getFridgeIdx());

        // then
        assertThat(cart).isEqualTo(cart1);
    }

    @DisplayName("냉장고 카트를 불러올 경우 예외처리를 확인한다.")
    @Test
    @Transactional
    void HandleCartException() {
        // given
        User user = createUser();
        userRepository.save(user);
        Fridge fridge = createFridge();
        fridgeRepository.save(fridge);
        FridgeUser fridgeUser = createFridgeUser(fridge, user);
        fridgeUserRepository.save(fridgeUser);
        Cart cart = createCart(fridge);
        cartRepository.save(cart);
        Food food = createFood();
        foodRepository.save(food);
        CartFood cartFood = createCartFood(cart, food);
        cartFoodRepository.save(cartFood);

        // when & then
        assertThrows(UserNotFoundException.class, () -> getCart(100L, fridge.getFridgeIdx()));
        assertThrows(FridgeNotFoundException.class, () -> getCart(user.getUserIdx(), 100L));
    }

    @DisplayName("냉장고 카트 별 음식 리스트를 불러온다.")
    @Test
    @Transactional
    void getCartFoods() {
        // given
        User user = createUser();
        userRepository.save(user);
        Fridge fridge = createFridge();
        fridgeRepository.save(fridge);
        FridgeUser fridgeUser = createFridgeUser(fridge, user);
        fridgeUserRepository.save(fridgeUser);
        Cart cart = createCart(fridge);
        cartRepository.save(cart);
        Food food = createFood();
        foodRepository.save(food);
        CartFood cartFood = createCartFood(cart, food);
        cartFoodRepository.save(cartFood);

        // when
        List<CartResponse> cartFoods = cartService.getCartFoods(fridge.getFridgeIdx(), user.getUserIdx());

        // then
        assertThat(cartFoods.size()).isEqualTo(1);
    }

    @DisplayName("카트에 음식을 추가한다.")
    @Test
    @Transactional
    void addCartFoods() {
        // given
        User user = createUser();
        userRepository.save(user);
        Fridge fridge = createFridge();
        fridgeRepository.save(fridge);
        FridgeUser fridgeUser = createFridgeUser(fridge, user);
        fridgeUserRepository.save(fridgeUser);
        Cart cart = createCart(fridge);
        cartRepository.save(cart);
        Food food = createFood();
        foodRepository.save(food);
        CartFood cartFood = createCartFood(cart, food);
        cartFoodRepository.save(cartFood);

        // when
        List<AddFoodRequest> lists = new ArrayList<>();
        lists.add(new AddFoodRequest("토마토", "채소"));
        lists.add(new AddFoodRequest("딸기", "과일"));
        AddFoodToCartRequest foodToCartInfo = new AddFoodToCartRequest(lists);

        this.cartService.addCartFoods(fridge.getFridgeIdx(), foodToCartInfo, user.getUserIdx());

        List<Food> foodLists = this.foodRepository.findAll();
        List<CartFood> cartFoodList = this.cartFoodRepository.findByCartAndIsEnable(cart, true);


        // then
        // 토마토는 이미 저장되어 있으므로 새로 생성이 되지 않고 딸기만 생성이 되는지 확인
        assertThat(foodLists.size()).isEqualTo(2);
        // 이미 토마토는 저장이 되어 있으므로, 딸기만 카트에 저장되어 있는지 확인
        assertThat(cartFoodList.size()).isEqualTo(2);
    }

    @DisplayName("카트에 음식을 삭제한다.")
    @Test
    @Transactional
    void deleteCartFoods() {
        // given
        User user = createUser();
        userRepository.save(user);
        Fridge fridge = createFridge();
        fridgeRepository.save(fridge);
        FridgeUser fridgeUser = createFridgeUser(fridge, user);
        fridgeUserRepository.save(fridgeUser);
        Cart cart = createCart(fridge);
        cartRepository.save(cart);
        Food food = createFood();
        foodRepository.save(food);
        CartFood cartFood = createCartFood(cart, food);
        cartFoodRepository.save(cartFood);

        // when
        // 현재 cascade가 적용 되어 있어서 자동으로 삭제 완
        this.cartFoodRepository.delete(cartFood);
        List<CartFood> cartFood1 = this.cartFoodRepository.findByCartAndIsEnable(cart, true);

        // then
        assertThat(cartFood1.size()).isEqualTo(0);
    }

    // 객체 생성 코드
    private User createUser() {
        return User.builder()
                .email("abc@abc.com")
                .provider(Provider.KAKAO)
                .nickname("아리")
                .profileImgKey("ariImgKey")
                .build();
    }

    private Fridge createFridge(){
        return Fridge.builder()
                .fridgeName("아리의 냉장고")
                .build();
    }

    private FridgeUser createFridgeUser(Fridge fridge, User user){
        return FridgeUser.builder()
                .fridge(fridge)
                .user(user)
                .role(FridgeRole.OWNER)
                .build();
    }

    private Cart createCart(Fridge fridge){
        return Cart.builder()
                .fridge(fridge)
                .build();
    }

    private CartFood createCartFood(Cart cart, Food food){
        return CartFood.builder()
                .cart(cart)
                .food(food)
                .build();
    }

    private Food createFood(){
        return Food.builder()
                .foodName("토마토")
                .foodImgKey("foods/tomato.jpg")
                .foodCategory(FoodCategory.VEGETABLE)
                .build();
    }

    // private 함수라 여기에 복붙해두었어요.
    private Cart getCart(Long userIdx, Long fridgeIdx) {
        User user = userRepository.findByUserIdxAndIsEnable(userIdx, true).orElseThrow(UserNotFoundException::new);
        Fridge fridge = fridgeRepository.findByFridgeIdxAndIsEnable(fridgeIdx, true).orElseThrow(FridgeNotFoundException::new);
        fridgeUserRepository.findByUserAndFridgeAndIsEnable(user, fridge, true).orElseThrow(FridgeUserNotFoundException::new);
        return cartRepository.findByFridge_FridgeIdxAndIsEnable(fridgeIdx, true).orElseThrow(CartNotFoundException::new);
    }
}