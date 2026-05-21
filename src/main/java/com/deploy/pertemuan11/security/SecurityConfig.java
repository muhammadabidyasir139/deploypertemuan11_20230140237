package com.deploy.pertemuan11.security;

import com.deploy.pertemuan11.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService; // Pastikan terimport
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder; // Ditambahkan import
import org.springframework.security.crypto.password.PasswordEncoder; // Ditambahkan import
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() { // Diubah menjadi bentuk method yang valid
        return new BCryptPasswordEncoder(); // Diperbaiki typo dari 'BCryptPasswordEncorder'
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) { // Diubah menjadi bentuk method yang valid
        return username -> {

            com.deploy.pertemuan11.model.User user = userRepository.findByUsername(username).orElseThrow(() ->
                    new UsernameNotFoundException("User tidak ditemukan"));

            return User
                    .withUsername(user.getUsername())
                    .password(user.getPassword())
                    .authorities("USER")
                    .build();
        };
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {

        http
                .csrf(csrf -> csrf.disable())

                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/register",
                                "/login",
                                "/css/**"
                        ).permitAll()
                        .anyRequest().authenticated()
                )

                .formLogin( login -> login
                        .loginPage("/login")
                        .defaultSuccessUrl("/home", true)
                        .permitAll()
                )

                .logout( logout -> logout
                        .logoutSuccessUrl("/login")
                );

        return http.build();
    }
}