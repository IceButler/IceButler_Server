package com.example.icebutler_server.global.resolver;

import com.example.icebutler_server.global.exception.BaseException;
import com.example.icebutler_server.global.util.TokenUtils;
import com.example.icebutler_server.user.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.core.env.Environment;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.validation.constraints.NotNull;

import static com.example.icebutler_server.global.exception.ReturnCode.*;


@RequiredArgsConstructor
@Component
public class LoginResolver implements HandlerMethodArgumentResolver{

    private final TokenUtils tokenUtils;
    private final Environment env;
    private final UserServiceImpl userService;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(IsLogin.class) && Long.class.equals(parameter.getParameterType());
    }

    @Nullable
    @Override
    public Object resolveArgument(@NotNull MethodParameter parameter,
                                  ModelAndViewContainer modelAndViewContainer,
                                  @NotNull NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory) {

        Auth auth = parameter.getMethodAnnotation(Auth.class);
        if(auth == null) throw new BaseException(INTERNAL_SERVER_ERROR);

        String header = webRequest.getHeader("Authorization");
        if(!StringUtils.hasText(header)) throw new BaseException(NULL_TOKEN);

        String accessToken = tokenUtils.separateAuthType(header);
        tokenUtils.isValidToken(accessToken);

        Long userId = Long.valueOf(tokenUtils.getJwtContents(accessToken));
        if(!tokenUtils.isTokenExists(accessToken)) throw new BaseException(EXPIRED_TOKEN);
        userService.validateUser(userId);

        return userId;
    }
}
