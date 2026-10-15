package br.cefetmg.grow.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import br.cefetmg.grow.security.JwtAuthenticationFilter;
import org.springframework.http.HttpMethod;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

        // 🟢 Reativando a injeção do filtro JWT
        private final JwtAuthenticationFilter jwtAuthenticationFilter;

        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
                http
                                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                                .csrf(csrf -> csrf.disable())
                                .sessionManagement(session -> session
                                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                                .authorizeHttpRequests(auth -> auth
                                                // 🟢 Libera o auth COM e SEM o prefixo /api (cobre /auth/** e
                                                // /api/auth/**)
                                                .requestMatchers("/auth/**", "/api/auth/**", "/swagger-ui/**",
                                                                "/v3/api-docs/**")
                                                .permitAll()

                                                // 🟢 Libera o cadastro de usuários
                                                .requestMatchers(HttpMethod.POST, "/api/usuarios").permitAll()

                                                // 🟢 Qualquer outra rota exigirá um token JWT válido
                                                .anyRequest().authenticated())
                                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

                return http.build();
        }

        @Bean
        public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
                        throws Exception {
                return authenticationConfiguration.getAuthenticationManager();
        }

@Bean
        public CorsConfigurationSource corsConfigurationSource() {
                CorsConfiguration configuration = new CorsConfiguration();

                // 🟢 Permite qualquer origem para testes mobile (ou adicione "http://192.168.1.116")
                configuration.setAllowedOriginPatterns(List.of("*")); // Use setAllowedOriginPatterns em vez de setAllowedOrigins quando usar "*" com credentials

                configuration.setAllowedMethods(
                                Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS", "HEAD"));
                configuration.setAllowedHeaders(List.of("*"));
                configuration.setAllowCredentials(true);

                UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
                source.registerCorsConfiguration("/**", configuration);
                return source;
        }
}