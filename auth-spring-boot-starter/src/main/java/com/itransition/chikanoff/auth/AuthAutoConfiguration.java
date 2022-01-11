package com.itransition.chikanoff.auth;

import com.itransition.chikanoff.auth.jwt.AuthEntryPointJwt;
import com.itransition.chikanoff.auth.jwt.AuthTokenFilter;
import com.itransition.chikanoff.auth.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(AuthProperties.class)
public class AuthAutoConfiguration {
    @Autowired
    private AuthProperties properties;

    @Bean
    @ConditionalOnMissingBean
    public JwtUtils jwtUtils() {
        JwtUtils utils = new JwtUtils();
        utils.setJwtSecret(properties.getJwtSecret());
        utils.setJwtExpirationMs(properties.getJwtExpirationMs());
        return utils;
    }

    @Bean
    @ConditionalOnMissingBean
    public AuthTokenFilter authTokenFilter() {
        return new AuthTokenFilter();
    }

    @Bean
    @ConditionalOnMissingBean
    public AuthEntryPointJwt authEntryPointJwt() {
        return new AuthEntryPointJwt();
    }
}
