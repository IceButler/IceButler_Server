package com.example.icebutler_server.chae_eun.user;

import com.example.icebutler_server.user.dto.assembler.UserAssembler;
import com.example.icebutler_server.user.dto.request.PatchProfileReq;
import com.example.icebutler_server.user.dto.request.PostNicknameReq;
import com.example.icebutler_server.user.dto.response.PostNickNameRes;
import com.example.icebutler_server.user.entity.Provider;
import com.example.icebutler_server.user.entity.User;
import com.example.icebutler_server.user.repository.UserRepository;
import com.example.icebutler_server.user.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class PatchUserProfileTest {
    @Autowired
    private UserAssembler assembler;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;


    @DisplayName("닉네임이 올바른 형식을 지켰는지 확인한다.")
    @Test
    void isValidNickNameTest(){
        // given
        String nickName = "아아";
        // when
        Boolean validNickname = this.assembler.isValidNickname(nickName);
        // then
        assertTrue(validNickname);
    }

    @DisplayName("닉네임이 올바른 형식이 아닌 경우를 확인한다.")
    @Test
    void isInvalidNickNameTest(){
        // given
        String nickName = "아아;;;"; // 특수 문자를 삽입한 경우
        String nickName2 = "dkdkdkdkdkddkdkdkdkdkdkdkdkd"; // 8글자 이상 입력한 경우
        // when
        Boolean invalidNickname = this.assembler.isValidNickname(nickName);
        Boolean invalidNickname2 = this.assembler.isValidNickname(nickName2);
        // then
        assertFalse(invalidNickname);
        assertFalse(invalidNickname2);
    }

    @DisplayName("닉네임 중복을 확인한다.")
    @Test
    @Transactional
    void duplicateCheckUserNicknameTest(){
        // given
        User user = createUser();
        userRepository.save(user);
        String nickname = "아리";
        // when
        PostNickNameRes nickNameRes = this.userService.checkNickname(new PostNicknameReq(nickname));

        // then
        assertTrue(nickNameRes.isExistence());
    }

    @DisplayName("사용자의 프로필 정보를 변경한다.")
    @Test
    @Transactional
    void patchUserProfileTest(){
        // given
        User user  = createUser();
        userRepository.save(user);
        // when
        this.userService.modifyProfile(user.getUserIdx(), new PatchProfileReq("아아", "userImgKey"));
        // then
        assertThat("아아").isEqualTo(user.getNickname());
        assertThat("userImgKey").isEqualTo(user.getProfileImgKey());
    }

    private User createUser() {
        return User.builder()
                .email("abc@abc.com")
                .provider(Provider.KAKAO)
                .nickname("아리")
                .profileImgKey("ariImgKey")
                .build();
    }
}
