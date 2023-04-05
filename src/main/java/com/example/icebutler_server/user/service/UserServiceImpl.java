package com.example.icebutler_server.user.service;

//import com.example.icebutler_server.global.util.redis.template.RedisTemplateService;
import com.example.icebutler_server.user.dto.response.GetProfileRes;
import com.example.icebutler_server.user.dto.response.UserSearchRes;
import com.example.icebutler_server.user.entity.User;
import com.example.icebutler_server.user.entity.vo.UserType;
import com.example.icebutler_server.user.exception.UserAlreadyDeleteException;
import com.example.icebutler_server.user.exception.UserNotFoundException;
import com.example.icebutler_server.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
//    private final UserOutRepository userOutRepository;
//    private final UserWithdrawalRepository userWithdrawalRepository;

//    private final RedisTemplateService redisTemplateService;

    @Override
    @Transactional
    public Boolean deleteUser(Long userIdx, String reason) {
        User user=userRepository.findByUserIdx(userIdx).orElseThrow(UserNotFoundException::new);
        if(user.getUserType().equals(UserType.DELETE)) throw new UserAlreadyDeleteException();
        user.setUserType(UserType.DELETE);
//        redisTemplateService.deleteUserRefreshToken(userIdx);
//        userOutRepository.save(UserOut.builder().user(user).reason(reason).build());
//        userWithdrawalRepository.save(UserWithdrawal.builder().user(user).reason(reason).build());
        return true;
    }

    @Override
    public Boolean logout(Long userIdx) {
        userRepository.findByUserIdx(userIdx).orElseThrow(UserNotFoundException::new);
//        redisTemplateService.deleteUserRefreshToken(userIdx);
        return null;
    }

    @Override
    public GetProfileRes getProfile(Long userIdx){
User user=userRepository.findById(userIdx).orElseThrow(UserNotFoundException::new);
return new GetProfileRes(user.getEmail(),user.getNickname(),user.getProfileImage());
    }

    @Override
    public UserSearchRes searchUsers(Long userIdx) {
        User user=userRepository.findById(userIdx).orElseThrow(UserAlreadyDeleteException::new);

        return UserSearchRes.builder()
                .userNickName(user.getNickname())
                .userId(user.getUserIdx())
                .build();
    }
}
