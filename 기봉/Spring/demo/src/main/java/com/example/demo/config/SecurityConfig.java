package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // CSRF 보호 비활성화
                .authorizeHttpRequests((authz) -> authz
                        .anyRequest().permitAll() // 모든 요청에 대한 접근 허용
                )
                .formLogin().disable() // 폼 로그인 비활성화
                .httpBasic().disable(); // HTTP Basic 인증 비활성화

        return http.build();
    }
}
