package com.example.icebutler_server.seunghak;
import com.example.icebutler_server.cart.dto.cart.request.AddFoodRequest;
import com.example.icebutler_server.cart.dto.cart.request.AddFoodToCartRequest;
import com.example.icebutler_server.cart.dto.cart.request.RemoveFoodFromCartRequest;
import com.example.icebutler_server.cart.dto.cart.response.CartResponse;
import com.example.icebutler_server.cart.entity.cart.Cart;
import com.example.icebutler_server.cart.entity.cart.CartFood;
import com.example.icebutler_server.cart.exception.CartNotFoundException;
import com.example.icebutler_server.cart.repository.cart.CartFoodRepository;
import com.example.icebutler_server.cart.repository.cart.CartRepository;
import com.example.icebutler_server.cart.service.CartService;
import com.example.icebutler_server.cart.service.CartServiceImpl;
import com.example.icebutler_server.food.dto.response.FoodResponse;
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
import com.example.icebutler_server.global.sqs.AmazonSQSSender;
import com.example.icebutler_server.user.entity.Provider;
import com.example.icebutler_server.user.entity.User;
import com.example.icebutler_server.user.exception.UserNotFoundException;
import com.example.icebutler_server.user.repository.UserRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
//@RunWith(SpringRunner.class)

@ExtendWith(MockitoExtension.class)
class CartServiceTest {
    @Mock
    private CartRepository cartRepository;

    @Mock
    private CartFoodRepository cartFoodRepository;
    @Mock
    private UserRepository userRepository;

    @Mock
    private FridgeRepository fridgeRepository;


    @Mock
    private FoodRepository foodRepository;

    @InjectMocks
    private CartServiceImpl cartService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetCartFoods() {
        Long fridgeIdx = 1L;
        Long userIdx = 1L;


        Cart cart = Cart.builder().fridge(Fridge.builder().build()).build();
        List<CartFood> cartFoods = Arrays.asList(
                CartFood.builder().food(Food.builder().foodCategory(FoodCategory.MEAT).build()).build(),
                CartFood.builder().food(Food.builder().foodCategory(FoodCategory.FRUIT).build()).build(),
                CartFood.builder().food(Food.builder().foodCategory(FoodCategory.MEAT).build()).build()
        );
        when(cartRepository.findByFridge_FridgeIdxAndIsEnable(eq(fridgeIdx), eq(true))).thenReturn(Optional.of(cart));
        when(cartFoodRepository.findByCartAndFood_FoodCategoryAndIsEnableOrderByCreatedAt(
                any(Cart.class), any(FoodCategory.class), eq(true)
        )).thenReturn(cartFoods);
        when(userRepository.findByUserIdxAndIsEnable(anyLong(), eq(true))).thenReturn(Optional.of(mock(User.class)));

        List<CartResponse> result = cartService.getCartFoods(fridgeIdx, userIdx);

        Assertions.assertEquals(2, result.size());

        CartResponse cartResponse1 = result.get(0);
        Assertions.assertEquals(FoodCategory.MEAT, cartResponse1.getCategory());
        Assertions.assertEquals(2, cartResponse1.getCartFoods().size());

        CartResponse cartResponse2 = result.get(1);
        Assertions.assertEquals(FoodCategory.FRUIT, cartResponse2.getCategory());
        Assertions.assertEquals(1, cartResponse2.getCartFoods().size());

        verify(cartRepository, times(1)).findByFridge_FridgeIdxAndIsEnable(eq(fridgeIdx), eq(true));
        verify(cartFoodRepository, times(2)).findByCartAndFood_FoodCategoryAndIsEnableOrderByCreatedAt(
                any(Cart.class), any(FoodCategory.class), eq(true)
        );
    }

    @Test
    public void testAddCartFoods() {
        Long fridgeIdx = 1L;
        Long userIdx = 1L;

        AddFoodToCartRequest request = new AddFoodToCartRequest();
        List<AddFoodRequest> foodRequests = Arrays.asList(
                new AddFoodRequest(),
                new AddFoodRequest()
        );
        request.setFoodRequests(foodRequests);

        Cart cart = Cart.builder().fridge(Fridge.builder().build()).build();

        Food food1 = Food.builder().foodName("테스트용 음식이름").foodCategory(FoodCategory.FRUIT).build();
        Food food2 = Food.builder().foodName("테스트용 음식이름2").foodCategory(FoodCategory.MEAT).build();

        when(cartRepository.findByFridge_FridgeIdxAndIsEnable(eq(fridgeIdx), eq(true))).thenReturn(Optional.of(cart));

        when(foodRepository.findByFoodNameAndFoodCategory(eq("이승학"), eq(FoodCategory.FRUIT))).thenReturn(null);
        when(foodRepository.save(any(Food.class))).thenReturn(food1);

        when(foodRepository.findByFoodNameAndFoodCategory(eq("승학"), eq(FoodCategory.MEAT))).thenReturn(food2);

        List<CartFood> existingCartFoods = Arrays.asList(
                CartFood.builder().food(food1).build()
        );
        when(cartFoodRepository.findByCartAndIsEnable(eq(cart), eq(true))).thenReturn(existingCartFoods);

        List<CartFood> savedCartFoods = Arrays.asList(
                CartFood.builder().food(food1).build(),
                CartFood.builder().food(food2).build()
        );
        when(cartFoodRepository.saveAll(anyList())).thenReturn(savedCartFoods);

        cartService.addCartFoods(fridgeIdx, request, userIdx);

        verify(cartRepository, times(1)).findByFridge_FridgeIdxAndIsEnable(eq(fridgeIdx), eq(true));

        verify(foodRepository, times(2)).findByFoodNameAndFoodCategory(anyString(), any(FoodCategory.class));
        verify(foodRepository, times(1)).save(any(Food.class));

        verify(cartFoodRepository, times(1)).findByCartAndIsEnable(eq(cart), eq(true));
        verify(cartFoodRepository, times(1)).saveAll(anyList());
    }

    @Test
    public void testDeleteCartFoods() {
        Fridge fridge = Fridge.builder()
                .fridgeName("테스트용 내 냉장고")
                .fridgeComment("테스트용 코멘트")
                .build();
        fridgeRepository.save(fridge);

        User user = User.builder()
                .provider(Provider.APPLE)
                .email("test@naver.com")
                .nickname("테스트용 닉네임")
                .build();
        userRepository.save(user);

        FridgeUser fridgeUser = FridgeUser.builder()
                .user(user)
                .fridge(fridge)
                .role(FridgeRole.MEMBER)
                .build();

        Cart cart = Cart.builder()
                .fridge(fridge)
                .build();
        cartRepository.save(cart);

        Food food1 = Food.builder()
                .foodName("ㅇㅇ")
                .foodImgKey("테스트용 이미지")
                .foodCategory(FoodCategory.FRUIT)
                .build();
        foodRepository.save(food1);

        Food food2 = Food.builder()
                .foodName("ㅇㅇㅇ")
                .foodImgKey("테스트용 이미지2")
                .foodCategory(FoodCategory.FRUIT)
                .build();
        foodRepository.save(food2);

        CartFood cartFood1 = CartFood.builder()
                .food(food1)
                .cart(cart)
                .build();
        cartFoodRepository.save(cartFood1);

        CartFood cartFood2 = CartFood.builder()
                .food(food2)
                .cart(cart)
                .build();
        cartFoodRepository.save(cartFood2);

        RemoveFoodFromCartRequest request = new RemoveFoodFromCartRequest();
        List<Long> foodIdxes = new ArrayList<>();
        foodIdxes.add(food1.getFoodIdx());
        foodIdxes.add(food2.getFoodIdx());
        request.setFoodIdxes(foodIdxes);

        Long fridgeIdx = fridge.getFridgeIdx();
        Long userIdx = user.getUserIdx();
//        CartService cartService = new CartService();
//        cartService.deleteCartFoods(fridgeIdx, request, userIdx);

        List<CartFood> remainingCartFoods = cartFoodRepository.findByCartIdxAndFoodIdxIn(cart.getCartIdx(), foodIdxes);
        assertEquals(0, remainingCartFoods.size());
    }



}
