package com.example.icebutler_server.admin.service;


import com.example.icebutler_server.admin.dto.condition.SearchCond;
import com.example.icebutler_server.admin.dto.request.JoinRequest;
import com.example.icebutler_server.admin.dto.request.LoginRequest;
import com.example.icebutler_server.admin.dto.request.WithDrawRequest;
import com.example.icebutler_server.admin.dto.response.AdminResponse;
import com.example.icebutler_server.admin.dto.response.LogoutResponse;
import com.example.icebutler_server.admin.dto.response.PostAdminRes;
import com.example.icebutler_server.admin.dto.response.UserResponse;
import com.example.icebutler_server.admin.entity.Admin;
import com.example.icebutler_server.admin.exception.AdminNotFoundException;
import com.example.icebutler_server.admin.exception.PasswordNotMatchException;
import com.example.icebutler_server.admin.repository.AdminRepository;
import com.example.icebutler_server.global.feign.dto.AdminReq;
import com.example.icebutler_server.global.feign.feignClient.RecipeServerClient;
import com.example.icebutler_server.global.util.RedisTemplateService;
import com.example.icebutler_server.global.util.TokenUtils;
import com.example.icebutler_server.user.dto.response.MyProfileRes;
import com.example.icebutler_server.user.entity.User;
import com.example.icebutler_server.user.exception.UserNotFoundException;
import com.example.icebutler_server.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class AdminServiceImpl implements AdminService {

    private final TokenUtils tokenUtils;
    private final BCryptPasswordEncoder pwEncoder;
    private final AdminRepository adminRepository;
    private final UserRepository userRepository;
    private final RedisTemplateService redisTemplateService;
    private final RecipeServerClient recipeServerClient;

    @Transactional
    @Override
    public AdminResponse join(JoinRequest request)
    {
        Admin admin = adminRepository.save(request.toAdmin(pwEncoder.encode(request.getPassword())));
        recipeServerClient.addAdmin(AdminReq.toDto(admin));
        return AdminResponse.toDto(admin);
    }

    @Override
    public PostAdminRes login(LoginRequest request)
    {
        Admin admin = adminRepository.findByEmail(request.getEmail()).orElseThrow(AdminNotFoundException::new);
        if(!pwEncoder.matches(request.getPassword(), admin.getPassword())) throw new PasswordNotMatchException();
        return PostAdminRes.toDto(tokenUtils.createToken(admin.getAdminIdx(), admin.getEmail()));
    }

    @Override
    public void logout(Long adminIdx)
    {
        Admin admin = adminRepository.findByAdminIdxAndIsEnable(adminIdx, true).orElseThrow(UserNotFoundException::new);
        redisTemplateService.deleteUserRefreshToken(admin.getAdminIdx());
    }

    @Override
    public Page<UserResponse> search(
            Pageable pageable,
            String nickname,
            boolean active)
    {
        return adminRepository.findAllByNicknameAndActive(pageable, nickname, active);
    }
    @Transactional
    @Override
    public void withdraw(WithDrawRequest request)
    {
        User user = userRepository.findById(request.getUserIdx()).orElseThrow(UserNotFoundException::new);
        userRepository.delete(user);
    }
}
