package com.example.icebutler_server.global.resolver;

import com.sun.istack.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;


@RequiredArgsConstructor
@Component
public class LoginResolver implements HandlerMethodArgumentResolver{

//    private final TokenUtils tokenUtils;
//    private final Environment env;

    @Override
    public boolean supportsParameter(MethodParameter parameter)
    {
        return parameter.hasParameterAnnotation(IsLogin.class)
                                   &&
               LoginStatus.class.equals(parameter.getParameterType());
    }

    @Nullable
    @Override
    public Object resolveArgument(@NotNull MethodParameter parameter,
                                  ModelAndViewContainer mavContainer,
                                  @NotNull NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory) throws Exception
    {

//        Auth auth = parameter.getMethodAnnotation(Auth.class);
//
//        if (auth == null)
//            throw new AuthAnnotationIsNowhereException("토큰을 통해 userId를 추출하는 메서드에는 @Auth 어노테이션을 붙여주세요.");
//
//        String accessToken = webRequest.getHeader(env.getProperty("jwt.access_name"));
//        if(accessToken == null || !tokenUtils.isValidToken(tokenUtils.parseJustTokenFromFullToken(accessToken)))
//            return LoginStatus.getNotLoginStatus();

//        Long userId = tokenUtils.getIdxFromFullToken(accessToken);
//        if (!auth.optional() && userId == null) {
//            return LoginStatus.getNotLoginStatus();
//        }
        return LoginStatus.builder().isLogin(true).userIdx(1L).build();
    }
}