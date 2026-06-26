package com.Project.Auth_Vault.Config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {

        @Bean
        public SecurityFilterChain securityFilteChain(HttpSecurity httpSecurity) throws Exception{
            httpSecurity
                    .csrf(csrf -> csrf.disable())
                    .authorizeHttpRequests(auth ->auth
                            .requestMatchers("/auth/register").permitAll()
                            .requestMatchers("/auth/login").permitAll()
                            .requestMatchers("/api/admin/**").hasRole("ADMIN")
                            .requestMatchers("/api/user/**").hasRole("USER")
                            .anyRequest().permitAll()

                    );
            return httpSecurity.build();

        }

    @Bean
    public PasswordEncoder passwordEncoder(){
            return new BCryptPasswordEncoder();
    }

}

