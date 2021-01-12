package com.fatehole.safety.security;

import com.fatehole.commonutil.ResponseUtil;
import com.fatehole.commonutil.Result;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author helaos
 * @version 1.0.0
 * @date Create in 2021/01/11/13:23
 */
public class UnauthorizedEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) {
        ResponseUtil.out(response, Result.error());
    }
}
