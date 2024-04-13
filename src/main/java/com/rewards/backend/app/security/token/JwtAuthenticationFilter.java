package com.rewards.backend.app.security.token;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private Logger logger = LoggerFactory.getLogger(OncePerRequestFilter.class);
    
    @Autowired private JwtHelper jwtHelper;
    @Autowired private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException{
        String requestHeader = request.getHeader("Authorization");
        logger.info("Header: {}", requestHeader);
        String username = null;
        String token = null;

        if (requestHeader != null && requestHeader.startsWith("Bearer ")) {
            token = requestHeader.substring(7);
            try {
                username = this.jwtHelper.getUsernameFromToken(token);
            } catch (IllegalArgumentException | ExpiredJwtException | MalformedJwtException e) {
                logger.error("Token validation failed: {}", e.getMessage());
            }
        } else {
            logger.info("Invalid Header Value");
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            logger.info("User Details: {}", userDetails);

            Boolean validateToken = this.jwtHelper.validateToken(token, userDetails);
            if (Boolean.TRUE.equals(validateToken)) {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                logger.info("Token validation failed");
            }
        }

        filterChain.doFilter(request, response);
    }
    
    
    
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        String requestHeader = request.getHeader("Authorization");
//        logger.info("Header: {}", requestHeader);
//        String username = null;
//        String token = null;
//
//        if (requestHeader != null && requestHeader.startsWith("Bearer ")) {
//            token = requestHeader.substring(7);
//            try {
//                username = this.jwtHelper.getUsernameFromToken(token);
//            } catch (IllegalArgumentException | ExpiredJwtException | MalformedJwtException e) {
//                handleTokenException(response, e);
//                return;
//            }
//        } else {
//            logger.info("Invalid Header Value");
//        }
//
//        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
//            logger.info("User Details: {}", userDetails);
//
//            Boolean validateToken = this.jwtHelper.validateToken(token, userDetails);
//            if (Boolean.TRUE.equals(validateToken)) {
//                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//                SecurityContextHolder.getContext().setAuthentication(authentication);
//            } else {
//                handleTokenValidationFailure(response);
//                return;
//            }
//        }
//
//        filterChain.doFilter(request, response);
//    }
//
//    private void handleTokenException(HttpServletResponse response, Exception e) throws IOException {
//        logger.error("Token validation failed: {}", e.getMessage());
//
//        if (e instanceof ExpiredJwtException) {
//            ResponseHandler.generateResponseTokenResponse(response, HttpStatus.UNAUTHORIZED, "Authentication failed", "JWT token expired");
//        } else if (e instanceof MalformedJwtException) {
//            ResponseHandler.generateResponseTokenResponse(response, HttpStatus.UNAUTHORIZED, "Authentication failed", "Invalid JWT token");
//        } else {
//            // For other exceptions, return an UNAUTHORIZED response
//            ResponseHandler.generateResponseTokenResponse(response, HttpStatus.UNAUTHORIZED, "Authentication failed", "Invalid token");
//        }
//    }
//
//    private void handleTokenValidationFailure(HttpServletResponse response) throws IOException {
//        logger.info("Token validation failed");
//
//        // Return an UNAUTHORIZED response
//        ResponseHandler.generateResponseTokenResponse(response, HttpStatus.UNAUTHORIZED, "Authentication failed", "Invalid token");
//    }

}
