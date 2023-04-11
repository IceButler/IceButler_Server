package com.example.icebutler_server.user.service;

//import com.example.icebutler_server.global.util.RedisTemplateService;
import com.example.icebutler_server.user.dto.MyProfileRes;
import com.example.icebutler_server.user.entity.User;
import com.example.icebutler_server.user.exception.UserNotFoundException;
import com.example.icebutler_server.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
//    private final RedisTemplateService redisTemplateService;

    //유저 탈퇴
    @Override
    @Transactional
    public Boolean deleteUser(Long userIdx) {
        User user=userRepository.findByUserIdx(userIdx).orElseThrow(UserNotFoundException::new);
//        redisTemplateService.deleteUserRefreshToken(userIdx);
        return true;
    }

    //유저 로그아웃
    @Override
    public Boolean logout(Long userIdx) {
        userRepository.findByUserIdx(userIdx).orElseThrow(UserNotFoundException::new);
//        redisTemplateService.deleteUserRefreshToken(userIdx);
        return true;
    }

    //마이페이지 조회
    @Override
    public MyProfileRes myProfile(Long userIdx) {
        User user=userRepository.findByUserIdx(userIdx).orElseThrow(UserNotFoundException::new);

        return MyProfileRes.builder()
                .userIdx(user.getUserIdx())
                .nickName(user.getNickname())
                .profileImage(user.getProfileImage())
                .build();
    }
}
