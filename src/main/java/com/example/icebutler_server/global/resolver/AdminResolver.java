package com.example.icebutler_server.global.resolver;

import com.example.icebutler_server.global.exception.BaseException;
import com.example.icebutler_server.global.util.TokenUtils;
import com.sun.istack.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.core.env.Environment;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Objects;

import static com.example.icebutler_server.global.exception.ReturnCode.INTERNAL_SERVER_ERROR;


@RequiredArgsConstructor
@Component
public class AdminResolver implements HandlerMethodArgumentResolver{

    private final TokenUtils tokenUtils;
    private final Environment env;

    @Override
    public boolean supportsParameter(MethodParameter parameter)
    {
        return parameter.hasParameterAnnotation(IsAdminLogin.class)
                                   &&
               AdminLoginStatus.class.equals(parameter.getParameterType());
    }

    @Nullable
    @Override
    public Object resolveArgument(@NotNull MethodParameter parameter,
                                  ModelAndViewContainer mavContainer,
                                  @NotNull NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory) throws Exception
    {
        Admin admin = parameter.getMethodAnnotation(Admin.class);

        if (admin == null)
            throw new BaseException(INTERNAL_SERVER_ERROR);

        String accessToken = webRequest.getHeader(Objects.requireNonNull(env.getProperty("jwt.auth-header")));
        if(accessToken == null || !tokenUtils.isValidToken(tokenUtils.parseJustTokenFromFullToken(accessToken)))
            return AdminLoginStatus.getNotAdminLoginStatus();

        Long adminId = Long.valueOf(tokenUtils.getUserIdFromFullToken(accessToken));

        if (!admin.optional() && adminId == null) {
            return AdminLoginStatus.getNotAdminLoginStatus();
        }

        return AdminLoginStatus.builder().isLogin(true).adminId(adminId).build();
    }
}
