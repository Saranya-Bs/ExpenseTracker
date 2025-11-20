package com.expensetracker.expense_tracker.filter;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.expensetracker.expense_tracker.entity.CustomUserDetails;
import com.expensetracker.expense_tracker.service.CustomUserDetailsService;
import com.expensetracker.expense_tracker.util.JWTUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired
    JWTUtil jwtUtil;

    @Autowired
    CustomUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        String token = null;
        String username = null;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
            username = jwtUtil.extractClaims(token).getSubject();
        }
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            CustomUserDetails details = (CustomUserDetails) userDetailsService.loadUserByUsername(username);
            if (jwtUtil.validateToken(username, details, token)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(details, null,
                        details.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
    }

}
