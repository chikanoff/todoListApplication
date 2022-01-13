package com.itransition.chikanoff.auth;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "auth.prop")
@Getter
@Setter
public class AuthProperties {
    private String jwtSecret;
    private int jwtExpirationMs;
}
