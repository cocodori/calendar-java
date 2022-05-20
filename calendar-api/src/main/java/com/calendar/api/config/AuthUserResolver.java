package com.calendar.api.config;

import com.calendar.api.dto.AuthUser;
import com.calendar.core.exception.CalendarException;
import com.calendar.core.exception.ErrorCode;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import static com.calendar.api.service.LoginService.LOGIN_SESSION_KEY;

public class AuthUserResolver implements HandlerMethodArgumentResolver {
    /**
     * 파라미터가 AuthUser.class라면 resolveArgument 실행한다.
     * @param parameter
     * @return
     */
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return AuthUser.class.isAssignableFrom(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        final Long userId = (Long) webRequest.getAttribute(LOGIN_SESSION_KEY, WebRequest.SCOPE_SESSION);
        if (userId == null)
            throw new CalendarException(ErrorCode.BAD_REQUEST);
        return AuthUser.of(userId);
    }
}
