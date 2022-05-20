package com.calendar.api.service;

import com.calendar.api.dto.LoginReq;
import com.calendar.api.dto.SignUpReq;
import com.calendar.core.domain.entity.User;
import com.calendar.core.dto.UserCreateReq;
import com.calendar.core.exception.CalendarException;
import com.calendar.core.exception.ErrorCode;
import com.calendar.core.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginService {
    public final static String  LOGIN_SESSION_KEY = "USER_ID";
    private final UserService userService;

    /**
     * UserService에 Create를 담당.
     * 이미 존재하는 유저 검증은 userService의 몫.
     * 생성되면 session에 담고 return
     * @param signUpReq
     * @param session
     */
    @Transactional
    public void signUp(SignUpReq signUpReq, HttpSession session) {
        final User user = userService.create(new UserCreateReq(
                signUpReq.getName(),
                signUpReq.getEmail(),
                signUpReq.getPassword(),
                signUpReq.getBirthday()
        ));
        session.setAttribute(LOGIN_SESSION_KEY, user);
    }

    /**
     * 세션 값이 있으면 return
     * 세션 값이 없으면 비밀번호 체 후에 로그인 & 세션에 담고 리턴
     * @param loginReq
     * @param session
     */
    @Transactional
    public void login(LoginReq loginReq, HttpSession session) {
        final Long userId = (Long) session.getAttribute(LOGIN_SESSION_KEY);
        if (userId != null) return;
        final Optional<User> user = userService.findPwMatchUser(loginReq.getEmail(), loginReq.getPassword());
        if (user.isPresent())
            session.setAttribute(LOGIN_SESSION_KEY, user.get().getId());
        else
            throw new CalendarException(ErrorCode.PASSWORD_NOT_MATCH);
    }

    @Transactional
    public void logout(HttpSession session) {
        session.removeAttribute(LOGIN_SESSION_KEY);
    }
}
