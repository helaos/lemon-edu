package com.fatehole.safety.security;

import com.fatehole.commonutil.ResponseUtil;
import com.fatehole.commonutil.Result;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author helaos
 * @version 1.0.0
 * @date Create in 2021/01/11/13:28
 */
public class TokenLogoutHandler implements LogoutHandler {

    private final TokenManager tokenManager;

    private final RedisTemplate<String, Object> redisTemplate;

    public TokenLogoutHandler(TokenManager tokenManager, RedisTemplate<String, Object> redisTemplate) {
        this.tokenManager = tokenManager;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String token = request.getHeader("token");
        if (token != null) {
            tokenManager.removeToken(token);
            //清空当前用户缓存中的权限数据
            String userName = tokenManager.getUserFromToken(token);
            redisTemplate.delete(userName);
        }
        ResponseUtil.out(response, Result.ok());
    }
}
