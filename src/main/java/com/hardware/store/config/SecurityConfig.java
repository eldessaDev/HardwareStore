package com.hardware.store.config;

// 1. IMPORTACIONES DE SPRING FRAMEWORK (Contexto)
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// 2. IMPORTACIONES DE ENCRIPTACIÓN
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

// 3. IMPORTACIONES DE SEGURIDAD WEB
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

@Configuration
public class SecurityConfig {

    // --- BEAN 1: EL ENCRIPTADOR (El que usas en UserServiceImpl) ---
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // --- BEAN 2: LA PUERTA DE ENTRADA (Reglas de acceso) ---
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Desactivamos CSRF porque vamos a usar Postman (API REST)
                .csrf(AbstractHttpConfigurer::disable)

                // Definimos quién puede entrar
                .authorizeHttpRequests(auth -> auth
                        // Permitimos entrar a TODO sin contraseña (Modo Desarrollo)
                        // OJO: "/**" significa "cualquier ruta"
                        .requestMatchers("/**").permitAll()
                );

        return http.build();
    }
}