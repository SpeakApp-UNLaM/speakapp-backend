package com.guba.spring.speakappbackend.security.filters;

import com.guba.spring.speakappbackend.security.services.JwtService;
import com.guba.spring.speakappbackend.security.services.CustomUserDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {

    private final CustomUserDetailService customUserDetailService;
    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        final String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        final String jwt = Optional
                .ofNullable(authorizationHeader)
                .filter(authHeader -> authHeader.startsWith("Bearer "))
                .map(authHeader -> authHeader.substring(7))
                .orElse(null);
        final String username = Optional
                .ofNullable(jwt)
                .map(this.jwtService::getUsername)
                .orElse(null);

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null ) {
            UserDetails u = customUserDetailService.loadUserByUsername(username);

            if (jwtService.isJwtValid(jwt, u)) {

                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(u, null, u.getAuthorities());
                //Authentication authentication = this.authManager.authenticate(authenticationToken);
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }

        }
        filterChain.doFilter(request, response);
    }
}
