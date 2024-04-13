package com.rewards.backend.app.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.rewards.backend.app.security.token.JwtAuthenticationEntryPoint;
import com.rewards.backend.app.security.token.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig  {

    @Autowired UserDetailsService userDetailsService;
    
    @Autowired PasswordEncoder passwordEncoder;

//    @Autowired private RoleHierarchy roleHierarchy;
    
//    @Autowired RoleHierarchyImpl roleHierarchyImpl;
    
    @Autowired
    private JwtAuthenticationEntryPoint point;

    @Autowired
    private JwtAuthenticationFilter filter;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
            .cors(request -> new CorsConfiguration().applyPermitDefaultValues())
            .headers(headers -> {
                headers.contentSecurityPolicy("default-src 'self' https://localhost:3000");
            })
            /* swagger-ui/**",
                        "/swagger-resources/*",
                        "/v3/api-docs/** */
            
            .authorizeHttpRequests(requests -> requests
                .requestMatchers("api/auth/**").permitAll()
                .requestMatchers("api/auth/token").permitAll()
                .requestMatchers("/**").permitAll()
                .requestMatchers("/api/v1/auth/**", 
                		"/v2/api-docs", 
                		"/v3/api-docs/**", 
                		"/swagger-resources/**", 
                		"/configuration/ui", 
                		"/configuration/security", 
                		"swagger-ui/**", 
                		"/webjars/**", 
                		"swagger-ui.html").permitAll()
                .requestMatchers("/**").permitAll()
            )
            .exceptionHandling(ex -> ex.authenticationEntryPoint(point))
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().requestMatchers("/swagger-ui/**", "/bus/v3/api-docs/**");
    }
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("*");
        configuration.addAllowedMethod("*"); 
        configuration.addAllowedHeader("*"); 
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
    @Bean
    DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }
    private String getUsernameFromToken() {
    	return this.getUsernameFromToken();
    }
}
