package com.app.security;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.app.model.UserAuth;
import com.app.service.impl.JwtTokenServiceImpl;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SecurityFilter extends OncePerRequestFilter {

    private final JwtTokenServiceImpl jwtTokenService;

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        var token = getToken(request);
        if (token == null) {
            filterChain.doFilter(request, response);
            return;
        }
        var user = extractUser(token);

        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            Collection<SimpleGrantedAuthority> authorities = Collections.emptyList();
            if (user.getRoles() != null) {
                authorities = user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.toString()))
                        .collect(Collectors.toList());
            }
            var authentication = new UsernamePasswordAuthenticationToken(user, null, authorities);

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }

    private String getToken(HttpServletRequest request) {
        var token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        return token;
    }

    private UserAuth extractUser(String token) {
        var userAuth = this.jwtTokenService.decodeToken(token);
        return userAuth;
    }

}
