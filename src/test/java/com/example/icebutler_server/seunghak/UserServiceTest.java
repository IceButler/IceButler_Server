package com.example.icebutler_server.seunghak;

import com.example.icebutler_server.global.feign.publisher.RecipeServerEventPublisher;
import com.example.icebutler_server.user.dto.request.PatchProfileReq;
import com.example.icebutler_server.user.entity.Provider;
import com.example.icebutler_server.user.entity.User;
import com.example.icebutler_server.user.repository.UserRepository;
import com.example.icebutler_server.user.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RecipeServerEventPublisher recipeServerEventPublisher;

    @InjectMocks
    private User user;

    private PatchProfileReq patchProfileReq;

    @Test
    void modifyProfile() {
        // Given
        Long userIdx = 1L;
        String newNickname = "테스트용 닉네임";
        String newProfileImgKey = "테스트용 이미지";

        patchProfileReq = new PatchProfileReq();
        patchProfileReq.setNickname(newNickname);
        patchProfileReq.setProfileImgKey(newProfileImgKey);

        when(userRepository.findByUserIdxAndIsEnable(eq(userIdx), eq(true))).thenReturn(Optional.of(user));

        // When
        user.modifyProfile(userIdx, patchProfileReq);

        // Then
        assertEquals(newNickname, user.getNickname());
        assertEquals(newProfileImgKey, user.getProfileImgKey());
        verify(recipeServerEventPublisher, times(1)).changeUserProfile(eq(user));
    }
}
