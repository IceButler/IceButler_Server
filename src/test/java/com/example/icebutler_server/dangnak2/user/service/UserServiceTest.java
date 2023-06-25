package com.example.icebutler_server.dangnak2.user.service;

import com.example.icebutler_server.global.util.redis.RedisTemplateService;
import com.example.icebutler_server.user.dto.request.PatchProfileReq;
import com.example.icebutler_server.user.entity.Provider;
import com.example.icebutler_server.user.entity.User;
import com.example.icebutler_server.user.exception.UserNotFoundException;
import com.example.icebutler_server.user.repository.UserRepository;
import com.example.icebutler_server.user.service.UserServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

  @InjectMocks
  UserServiceImpl userService;

  @Mock
  UserRepository userRepository;

  @Before
  public void setUp(){
    MockitoAnnotations.openMocks(this);
  }

  @Test
  @DisplayName("유저 프로필 편집 성공")
  public void 유저_프로필편집() {

    //given
    User actualUser = createUser();
    PatchProfileReq request = PatchProfileReq.builder().nickname("test").profileImgKey("test.png").build();

    //when
    lenient().when(userRepository.findByUserIdxAndIsEnable(actualUser.getUserIdx(), true)).thenReturn(Optional.of(actualUser));

    //then
    Assertions.assertTrue(userService.modifyProfile(actualUser.getUserIdx(), request));
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
