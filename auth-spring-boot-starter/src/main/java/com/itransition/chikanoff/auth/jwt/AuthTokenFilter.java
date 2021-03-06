package com.itransition.chikanoff.auth.jwt;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Log4j2
public class AuthTokenFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            Optional.ofNullable(parseJwt(request))
                    .filter(jwtUtils::validateJwtToken)
                    .map(userDetailsService::loadUserByUsername)
                    .map(user -> new UsernamePasswordAuthenticationToken(user, null))
                    .ifPresent(SecurityContextHolder.getContext()::setAuthentication);

            filterChain.doFilter(request, response);
        } catch (Exception e) {
            log.error("Cannot set user authentication: {}", e.getMessage());
        }
    }

    private String parseJwt(HttpServletRequest request) {
        final int bearerLength = 7;
        String headerAuth = request.getHeader("Authorization");

        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(bearerLength);
        }

        return null;
    }
}
