package com.login;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import com.login.jwt.JwtUtils;

import com.login.jwt.JwtFilter;
import  com.login.jwt.JwtUtils;
import  com.login.CorsAllowingFilter;
import  com.login.enums.Message;
import  com.login.model.User;
import  com.login.dao.UserRepository;
import  com.login.utils.MathUtil;

@Configuration
public class SecurityBeansDefinitions {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserRepository userRepository;

    @Bean
    public AuthenticationManager customAuthenticationManager() {
        return (Authentication authentication) -> {
            
          if (authentication.getPrincipal() == null) {
                throw new AuthenticationServiceException(Message.forbidden.toString());
            }
            User user = userRepository.findByEmail(authentication.getPrincipal().toString());
            if (user == null || !user.isRegistered() ) {
                throw new AuthenticationServiceException(Message.forbidden.toString());
            }
            if (!user.getPassword().equals(MathUtil.md5(authentication.getCredentials().toString()))) {
                throw new AuthenticationServiceException(Message.forbidden.toString());
            }
            return new UsernamePasswordAuthenticationToken(authentication.getPrincipal(), authentication.getCredentials(), null);
        };
    }

    @Bean
    public JwtFilter jwtAuthenticationTokenFilter() {
        return new JwtFilter();
    }

    @Bean
    public JwtUtils jwtTokenUtil() {
        return new JwtUtils();
    }

    @Bean
    public CorsAllowingFilter corsAllowingFilter() {
        return new CorsAllowingFilter();
    }}