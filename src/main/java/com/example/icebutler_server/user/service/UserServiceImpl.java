package com.example.icebutler_server.user.service;

import com.example.icebutler_server.global.dto.response.ResponseCustom;
import com.example.icebutler_server.user.dto.UserSearchRes;
import com.example.icebutler_server.user.entity.User;
//import com.example.icebutler_server.user.repository.FridgeUserRepository;
import com.example.icebutler_server.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
//    private final FridgeUserRepository fridgeUserRepository;
//    private final UserOutRepository userOutRepository;
//    private final UserWithdrawalRepository userWithdrawalRepository;

//    private final RedisTemplateService redisTemplateService;

//    @Override
//    @Transactional
//    public Boolean deleteUser(Long userIdx) {
//        User user=userRepository.findByUserIdx(userIdx).orElseThrow(UserNotFoundException::new);
//        if(user.getUserIdx().equals(userIdx)) throw new UserAlreadyDeleteException();
////        redisTemplateService.deleteUserRefreshToken(userIdx);
////
//        return true;
//    }
//
//    @Override
//    public Boolean logout(Long userIdx) {
//        userRepository.findByUserIdx(userIdx).orElseThrow(UserNotFoundException::new);
////        redisTemplateService.deleteUserRefreshToken(userIdx);
//        return null;
//    }
//
//    @Override
//    public GetProfileRes getProfile(Long userIdx){
//        User user=userRepository.findById(userIdx).orElseThrow(UserNotFoundException::new);
//        return new GetProfileRes(user.getEmail(),user.getNickname(),user.getProfileImage());
//    }

    @Override
    public ResponseCustom<UserSearchRes> searchUsers(String nickName) {
        User user=userRepository.findByNickname(nickName);

        return ResponseCustom.OK(UserSearchRes.toDto(user));

    }

//    @Override
//    public ResponseCustom<FridgeUserRes> searchFridgeUsers(Long fridgeUserIdx) {
//        FridgeUser fridgeUser=fridgeUserRepository.findFridgeUserBy(fridgeUserIdx);
//
//        return ResponseCustom.OK(FridgeUserRes.toDto(fridgeUser));
//    }
}