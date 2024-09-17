package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @SuppressWarnings("removal")
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable()  // Desabilita CSRF para facilitar o acesso ao H2
            .headers().frameOptions().disable()  // Permite o uso de frames (necessário para o H2)
            .and()
            .authorizeHttpRequests((authz) -> authz
                .requestMatchers("/h2-console/**").permitAll()  // Permite acesso ao H2 Console
                .anyRequest().permitAll()  // Todas as outras requisições precisam de autenticação
            );
        return http.build();
    }
}
