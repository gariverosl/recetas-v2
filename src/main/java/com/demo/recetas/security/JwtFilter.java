package com.demo.recetas.security;

import java.io.IOException;
import java.util.Collections;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JwtFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
        String requestURI = request.getRequestURI();
        String authHeader = request.getHeader("Authorization");
        System.out.println("Processing request: " + requestURI);
        System.out.println("Auth header: " + authHeader);
    
        if (isPublicRoute(requestURI)) {
            System.out.println("Public route detected: " + requestURI);
            filterChain.doFilter(request, response);
            return;
        }
    
        try {
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                String token = authHeader.substring(7);
                String username = jwtUtil.extractUsername(token);
                System.out.println("Token extracted username: " + username);
    
                if (username != null && jwtUtil.isTokenValid(token, username)) {
                    UsernamePasswordAuthenticationToken authentication = 
                        new UsernamePasswordAuthenticationToken(username, null, Collections.emptyList());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    System.out.println("Authentication successful");
                    filterChain.doFilter(request, response);
                    return;
                }
            }
    
            System.out.println("No valid token found for protected route");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write("{\"error\": \"No autorizado\"}");
    
        } catch (Exception e) {
            System.out.println("Error processing token: " + e.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write("{\"error\": \"" + e.getMessage() + "\"}");
        } finally {
            if (SecurityContextHolder.getContext().getAuthentication() == null) {
                SecurityContextHolder.clearContext();
            }
        }
    }

    private boolean isPublicRoute(String uri) {
        return uri.startsWith("/css/") || 
               uri.startsWith("/js/") || 
               uri.startsWith("/images/") ||
               uri.equals("/") ||
               uri.equals("/login") ||
               uri.equals("/register") ||
               uri.equals("/buscar") ||

               uri.equals("/api/auth/login");
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return isPublicRoute(request.getRequestURI());
    }
}