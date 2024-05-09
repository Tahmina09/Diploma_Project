package com.example.diploma_project.config;

import com.example.diploma_project.services.ReaderDetailsService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


/**
 * Класс конфигурации безопасности приложения
 */
@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class WebSecurityConfig {
    private final ReaderDetailsService readerDetailsService;

    /**
     * Метод аутентификации пользователя
     * @param auth менеджер аутентификации
     * @throws Exception выбрасывает исключение
     */
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(readerDetailsService).
                passwordEncoder(passwordEncoder());
        auth.inMemoryAuthentication()
                .withUser("admin@gmail.com")
                .password("password123")
                .roles("ADMIN");
    }

    /**
     * Метод для фильтрации и авторизации пользователей
     * @param http безопасный http-канал
     * @return http-канал с установленными параметрами
     * @throws Exception выбрасывает исключение
     */
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/", "/home", "/register").permitAll()
                        .requestMatchers("/reader/**", "/readers","/book/**", "/books")
                        .hasRole("ADMIN")
                        .requestMatchers("/books", "/book/find/*", "/book/{id}")
                        .hasAnyRole("USER", "ADMIN")
                        .anyRequest().authenticated())
                .rememberMe(httpSecurityRememberMeConfigurer ->
                        httpSecurityRememberMeConfigurer.alwaysRemember(true))
                .logout(logout -> logout
                                .logoutUrl("/logout")
                                .deleteCookies("JSESSIONID")
                                .permitAll().logoutSuccessUrl("/"));
        return http.build();
    }


    /**
     * Метод хэширования пароля пользователя
     * @return захэшированный пароль
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
