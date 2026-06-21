package com.multitenancy.systems.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // 1. WAJIB: Matikan CSRF agar method POST/PUT bisa diakses tanpa token CSRF
                .csrf(AbstractHttpConfigurer::disable)

                // 2. Buka akses publik khusus untuk endpoint API Projects kita
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/projects/**").permitAll()
                        .anyRequest().authenticated()
                )

                // 3. Aktifkan HTTP Basic Auth untuk endpoint lain yang dikunci
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }

}
