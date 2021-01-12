package com.fatehole.safety.config;

import com.fatehole.safety.filter.TokenAuthenticationFilter;
import com.fatehole.safety.filter.TokenLoginFilter;
import com.fatehole.safety.security.DefaultPasswordEncoder;
import com.fatehole.safety.security.TokenLogoutHandler;
import com.fatehole.safety.security.TokenManager;
import com.fatehole.safety.security.UnauthorizedEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @author helaos
 * @version 1.0.0
 * @date Create in 2021/01/11/11:16
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class TokenWebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;

    private final RedisTemplate<String, Object> redisTemplate;

    private final TokenManager tokenManager;

    private final DefaultPasswordEncoder defaultPasswordEncoder;

    @Autowired
    public TokenWebSecurityConfig(UserDetailsService userDetailsService, RedisTemplate<String, Object> redisTemplate, TokenManager tokenManager, DefaultPasswordEncoder defaultPasswordEncoder) {
        this.userDetailsService = userDetailsService;
        this.redisTemplate = redisTemplate;
        this.tokenManager = tokenManager;
        this.defaultPasswordEncoder = defaultPasswordEncoder;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(defaultPasswordEncoder);
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/api/**",
                "/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger-ui.html/**"
        );
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.exceptionHandling()
                .authenticationEntryPoint(new UnauthorizedEntryPoint())
                .and().csrf().disable()
                .authorizeRequests()
                .anyRequest().authenticated()
                .and().logout().logoutUrl("/admin/acl/index/logout")
                .addLogoutHandler(new TokenLogoutHandler(tokenManager,redisTemplate)).and()
                .addFilter(new TokenLoginFilter(authenticationManager(), tokenManager, redisTemplate))
                .addFilter(new TokenAuthenticationFilter(authenticationManager(), tokenManager, redisTemplate)).httpBasic();
    }
}
