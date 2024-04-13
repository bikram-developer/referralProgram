package com.rewards.backend.app.security.token;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rewards.backend.ResponseHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException
    , HttpMessageNotReadableException{
//        String errorMessage = "Access Denied !! " + authException.getMessage();
    	String errorMessage = "You don't have required privilage to perform this action";
        // Use ResponseHandler.generateResponse to create the response
        ResponseEntity<Object> responseEntity = ResponseHandler.generateResponse(errorMessage, HttpStatus.UNAUTHORIZED, "Unauthorized");
 
        // Set the HTTP response status and content type
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");

        // Write the JSON response to the HttpServletResponse
        response.getWriter().write(new ObjectMapper().writeValueAsString(responseEntity.getBody()));
        response.getWriter().flush();
    }
}
