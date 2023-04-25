package com.shop.market.config.Oauth;

import com.shop.market.dto.SessionUser;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@RequiredArgsConstructor
@Component
public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver {
    private final HttpSession httpSession;

    @Override
    public boolean supportsParameter(MethodParameter parameter){
        boolean isLoginAnnotation = parameter.getParameterAnnotation(LoginUser.class) != null;
        boolean isUserClass = SessionUser.class.equals(parameter.getParameterType());

        return isLoginAnnotation && isUserClass;

    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer MAVContainer,
            NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception{
        return httpSession.getAttribute("user");
    }
}
