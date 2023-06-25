package com.example.icebutler_server.dangnak2.cart.service;

import com.example.icebutler_server.cart.dto.cart.request.AddFoodRequest;
import com.example.icebutler_server.cart.dto.cart.request.AddFoodToCartRequest;
import com.example.icebutler_server.cart.dto.cart.response.CartResponse;
import com.example.icebutler_server.cart.entity.cart.Cart;
import com.example.icebutler_server.cart.entity.cart.CartFood;
import com.example.icebutler_server.cart.service.CartServiceImpl;
import com.example.icebutler_server.food.entity.Food;
import com.example.icebutler_server.food.entity.FoodCategory;
import com.example.icebutler_server.food.repository.FoodRepository;
import com.example.icebutler_server.fridge.entity.fridge.Fridge;
import com.example.icebutler_server.fridge.entity.fridge.FridgeUser;
import com.example.icebutler_server.fridge.repository.fridge.FridgeRepository;
import com.example.icebutler_server.fridge.repository.fridge.FridgeUserRepository;
import com.example.icebutler_server.global.entity.FridgeRole;
import com.example.icebutler_server.user.entity.Provider;
import com.example.icebutler_server.user.entity.User;
import com.example.icebutler_server.user.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
public class CartServiceTest {

  @InjectMocks
  CartServiceImpl cartService;

  @Mock
  FoodRepository foodRepository;

  @Mock
  UserRepository userRepository;

  @Mock
  FridgeRepository fridgeRepository;

  @Mock
  FridgeUserRepository fridgeUserRepository;

  @Before
  public void setUp(){
    MockitoAnnotations.openMocks(this);
  }

  @Test
  @DisplayName("장바구니 식품추가 성공(음식이 등록되어있는 경우)")
  public void 장바구니_식품추가_식품등록O() {

    // given
    User actualUser = createUser();
    Fridge fridge = createFridge();
    Cart cart = createCart(fridge);

    AddFoodRequest foodRequest1 = AddFoodRequest.builder().foodName("testFood1").foodCategory("간식").build();
    AddFoodRequest foodRequest2 = AddFoodRequest.builder().foodName("testFood2").foodCategory("반찬").build();
    AddFoodToCartRequest request = AddFoodToCartRequest.builder().foodRequests(List.of(foodRequest1, foodRequest2)).build();

    Food food1 = createFood(request.getFoodRequests().get(0).getFoodName(), request.getFoodRequests().get(0).getFoodCategory());
    Food food2 = createFood(request.getFoodRequests().get(1).getFoodName(), request.getFoodRequests().get(1).getFoodCategory());

    CartFood cartFood1 = createCartFood(cart, food1);
    CartFood cartFood2 = createCartFood(cart, food2);
    List<CartFood> cartFoodList = List.of(cartFood1, cartFood2);

    // when
    lenient().when(fridgeRepository.findByFridgeIdxAndIsEnable(fridge.getFridgeIdx(), true)).thenReturn(Optional.of(fridge));
    lenient().when(userRepository.findByUserIdxAndIsEnable(actualUser.getUserIdx(), true)).thenReturn(Optional.of(actualUser));
    lenient().when(foodRepository.findByFoodNameAndFoodCategory(request.getFoodRequests().get(0).getFoodName(), FoodCategory.getFoodCategoryByName(request.getFoodRequests().get(0).getFoodCategory()))).thenReturn(food1);
    lenient().when(foodRepository.findByFoodNameAndFoodCategory(request.getFoodRequests().get(1).getFoodName(), FoodCategory.getFoodCategoryByName(request.getFoodRequests().get(1).getFoodCategory()))).thenReturn(food2);

    // then
    assertTrue(cartService.addCartFoods(fridge.getFridgeIdx(), request, actualUser.getUserIdx()));
  }

  @Test
  @DisplayName("장바구니 식품 조회 성공")
  public void 장바구니_식품조회() {

    // given
    User actualUser = createUser();
    Fridge fridge = createFridge();
    FridgeUser fridgeUser = createFridgeUser(actualUser, fridge);

    // when
    lenient().when(userRepository.findByUserIdxAndIsEnable(actualUser.getUserIdx(), true)).thenReturn(Optional.of(actualUser));
    lenient().when(fridgeRepository.findByFridgeIdxAndIsEnable(fridge.getFridgeIdx(), true)).thenReturn(Optional.of(fridge));
    lenient().when(fridgeUserRepository.findByFridgeAndUserAndIsEnable(fridge, actualUser, true)).thenReturn(Optional.of(fridgeUser));
    List<CartResponse> response = cartService.getCartFoods(fridge.getFridgeIdx(), actualUser.getUserIdx());

    // then
    assertFalse(response.isEmpty());
  }

  @Test
  @DisplayName("장바구니 식품삭제 성공")
  public void 장바구니_식품삭제() {

    // given
    User actualUser = createUser();
    Fridge fridge = createFridge();
    FridgeUser fridgeUser = createFridgeUser(actualUser, fridge);

    // when
    lenient().when(userRepository.findByUserIdxAndIsEnable(actualUser.getUserIdx(), true)).thenReturn(Optional.of(actualUser));
    lenient().when(fridgeRepository.findByFridgeIdxAndIsEnable(fridge.getFridgeIdx(), true)).thenReturn(Optional.of(fridge));
    lenient().when(fridgeUserRepository.findByFridgeAndUserAndIsEnable(fridge, actualUser, true)).thenReturn(Optional.of(fridgeUser));

    // then

  }


  private FridgeUser createFridgeUser(User actualUser, Fridge fridge) {
    return FridgeUser.builder()
            .user(actualUser)
            .fridge(fridge)
            .role(FridgeRole.OWNER)
            .build();
  }

  private CartFood createCartFood(Cart cart, Food food) {
    return CartFood.builder()
            .cart(cart)
            .food(food)
            .build();

  }

  private Cart createCart(Fridge fridge) {
    return Cart.builder()
            .fridge(fridge)
            .build();
  }

  private Food createFood(String name, String category) {
    return Food.builder()
            .foodCategory(FoodCategory.getFoodCategoryByName(category))
            .foodName(name)
            .foodImgKey("etc.png")
            .uuid(UUID.randomUUID())
            .build();
  }

  private Fridge createFridge() {
    return Fridge.builder()
            .fridgeName("testName")
            .fridgeComment("testComment")
            .build();
  }

  private User createUser() {
    return User.builder()
            .profileImgKey("test.png")
            .nickname("testNickname")
            .email("testEmail")
            .provider(Provider.KAKAO)
            .fcmToken("fcmToken")
            .build();
  }

}
