package com.example.icebutler_server.user.service;

import com.example.icebutler_server.global.util.redis.template.RedisTemplateService;
import com.example.icebutler_server.user.entity.User;
import com.example.icebutler_server.user.entity.UserOut;
import com.example.icebutler_server.user.entity.vo.UserType;
import com.example.icebutler_server.user.exception.UserAlreadyDeleteException;
import com.example.icebutler_server.user.exception.UserNotFoundException;
import com.example.icebutler_server.user.repository.UserOutRepository;
import com.example.icebutler_server.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final UserOutRepository userOutRepository;

    private final RedisTemplateService redisTemplateService;

    @Override
    @Transactional
    public Boolean deleteUser(Long userId, String reason) {
        User user=userRepository.findByUserIdx(userId).orElseThrow(UserNotFoundException::new);
        if(user.getUserType().equals(UserType.DELETE)) throw new UserAlreadyDeleteException();
        user.setUserType(UserType.DELETE);
        redisTemplateService.deleteUserRefreshToken(userId);
        userOutRepository.save(UserOut.builder().user(user).reason(reason).build());
        return true;


    }
}
