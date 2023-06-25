package com.example.icebutler_server.dangnak2.cart.controller;

import com.example.icebutler_server.cart.controller.CartController;
import com.example.icebutler_server.cart.dto.cart.response.CartResponse;
import com.example.icebutler_server.cart.entity.cart.Cart;
import com.example.icebutler_server.cart.entity.cart.CartFood;
import com.example.icebutler_server.cart.exception.CartNotFoundException;
import com.example.icebutler_server.cart.repository.cart.CartFoodRepository;
import com.example.icebutler_server.cart.repository.cart.CartRepository;
import com.example.icebutler_server.cart.service.CartServiceImpl;
import com.example.icebutler_server.food.dto.response.FoodResponse;
import com.example.icebutler_server.food.entity.FoodCategory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;

@WebMvcTest(CartController.class)
@AutoConfigureWebMvc
public class CartControllerTest {

  @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
  @Autowired
  private MockMvc mockMvc;

  @Autowired
  CartFoodRepository cartFoodRepository;
  @Autowired
  CartRepository cartRepository;

  @MockBean
  CartServiceImpl cartServiceImpl;

  @Test
  public void getCartFoodTest() throws Exception {

    FoodCategory foodCategory = FoodCategory.getFoodCategoryByName("사과");
    Cart cart = cartRepository.findByFridge_FridgeIdxAndIsEnable(156L, true).orElseThrow(CartNotFoundException::new);
    List<CartFood> cartFoodList = cartFoodRepository.findByCartAndFood_FoodCategoryAndIsEnableOrderByCreatedAt(cart, foodCategory, true);

    List<CartResponse> cartResponses = new ArrayList<>();
    CartResponse cartResponse = CartResponse.toDto(cartFoodList, foodCategory);
    cartResponses.add(cartResponse);

    given(cartServiceImpl.getCartFoods(156L, 18L)).willReturn(
            cartResponses
    );
  }
}
