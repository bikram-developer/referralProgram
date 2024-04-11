package com.rewards.backend.TEMP_PACKAGE;

import java.io.IOException;
import java.util.Enumeration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import
  org.springframework.web.filter.OncePerRequestFilter;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
  
@Component
public class LoggingFilter extends OncePerRequestFilter {
  
@Override
   protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
       // Log the request
       logRequest(request);

       // Proceed with the request
       filterChain.doFilter(request, response);
       logResponse(response);
   }

   private String getRequestParametersAsString(HttpServletRequest request) {
       StringBuilder parametersAsString = new StringBuilder();
       Enumeration<String> parameterNames = request.getParameterNames();

       while (parameterNames.hasMoreElements()) {
           String paramName = parameterNames.nextElement();
           String[] paramValues = request.getParameterValues(paramName);

           for (String paramValue : paramValues) {
               parametersAsString.append(paramName).append(": ").append(paramValue).append("\n");
           }
       }
       System.out.println(parametersAsString.toString());
       return parametersAsString.toString();
   }

   
   // change to string request
   private String getRequestHeadersAsString(HttpServletRequest request) {
       StringBuilder headersAsString = new StringBuilder();
       Enumeration<String> headerNames = request.getHeaderNames();

       while (headerNames.hasMoreElements()) {
           String headerName = headerNames.nextElement();
           String headerValue = request.getHeader(headerName);
           headersAsString.append(headerName).append(": ").append(headerValue).append("\n");
       }

       return headersAsString.toString();
   }

   private String getResponseHeadersAsString(HttpServletResponse response) {
       StringBuilder headersAsString = new StringBuilder();

       for (String headerName : response.getHeaderNames()) {
           String headerValue = response.getHeader(headerName);
           headersAsString.append(headerName).append(": ").append(headerValue).append("\n");
       }

       return headersAsString.toString();
   }

   private void logRequest(HttpServletRequest request) {
       StringBuilder requestLog = new StringBuilder();
       requestLog.append("Request: ");
       requestLog.append(request.getMethod());
       requestLog.append(" ");
       requestLog.append(request.getRequestURI());
       requestLog.append("\nHeaders:\n");

       Enumeration<String> headerNames = request.getHeaderNames();
       while (headerNames.hasMoreElements()) {
           String headerName = headerNames.nextElement();
           String headerValue = request.getHeader(headerName);
           requestLog.append(headerName);
           requestLog.append(": ");
           requestLog.append(headerValue);
           requestLog.append("\n");
       }

       // Log request details
       System.out.println(requestLog.toString());
   }

   private void logResponse(HttpServletResponse response) {
       StringBuilder responseLog = new StringBuilder();
       responseLog.append("Response: ");
       responseLog.append(response.getStatus());
       responseLog.append("\nHeaders:\n");

       for (String headerName : response.getHeaderNames()) {
           String headerValue = response.getHeader(headerName);
           responseLog.append(headerName);
           responseLog.append(": ");
           responseLog.append(headerValue);
           responseLog.append("\n");
       }

       // Log response details
       System.out.println(responseLog.toString());
   }
  
  }
 
