package com.example.icebutler_server.global.resolver;

import com.example.icebutler_server.global.exception.BaseException;
import com.example.icebutler_server.global.util.TokenUtils;
import com.example.icebutler_server.user.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
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
public class LoginResolver implements HandlerMethodArgumentResolver {

    public static final String AUTH_HEADER_NAME = "Authorization";
    ;
    private final TokenUtils tokenUtils;
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
        checkMethodValidation(parameter);
        String header = getHeader(webRequest);
        String accessToken = getAccessToken(header);
        Long userId = getUserId(accessToken);
        return userId;
    }

    private void checkMethodValidation(MethodParameter parameter) {
        if (parameter.getMethodAnnotation(Auth.class) == null) {
            throw new BaseException(INTERNAL_SERVER_ERROR);
        }
    }

    private static String getHeader(NativeWebRequest webRequest) {
        String header = webRequest.getHeader(AUTH_HEADER_NAME);
        if (!StringUtils.hasText(header)) {
            throw new BaseException(NULL_TOKEN);
        }
        return header;
    }

    private String getAccessToken(String header) {
        String accessToken = tokenUtils.separateAuthType(header);
        tokenUtils.isValidToken(accessToken);
        if (!tokenUtils.isTokenExists(accessToken)) {
            throw new BaseException(EXPIRED_TOKEN);
        }
        return accessToken;
    }

    private Long getUserId(String accessToken) {
        Long userId = Long.valueOf(tokenUtils.getJwtContents(accessToken));
        userService.validateUser(userId);
        return userId;
    }
}
