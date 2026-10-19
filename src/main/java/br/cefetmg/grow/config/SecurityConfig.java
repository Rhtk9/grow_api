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

                                                // ===== Regras por perfil =====
                                                // Só PROFESSOR cria/altera/exclui turmas, atividades, disciplinas e conteúdos
                                                .requestMatchers(HttpMethod.POST, "/api/turmas", "/api/turmas/**").hasRole("PROFESSOR")
                                                .requestMatchers(HttpMethod.PUT, "/api/turmas/**").hasRole("PROFESSOR")
                                                .requestMatchers(HttpMethod.DELETE, "/api/turmas/**").hasRole("PROFESSOR")
                                                .requestMatchers(HttpMethod.POST, "/api/atividades", "/api/atividades/**").hasRole("PROFESSOR")
                                                .requestMatchers(HttpMethod.PUT, "/api/atividades/**").hasRole("PROFESSOR")
                                                .requestMatchers(HttpMethod.PATCH, "/api/atividades/**").hasRole("PROFESSOR")
                                                .requestMatchers(HttpMethod.DELETE, "/api/atividades/**").hasRole("PROFESSOR")
                                                .requestMatchers(HttpMethod.POST, "/api/disciplinas", "/api/disciplinas/**").hasRole("PROFESSOR")
                                                .requestMatchers(HttpMethod.PUT, "/api/disciplinas/**").hasRole("PROFESSOR")
                                                .requestMatchers(HttpMethod.PATCH, "/api/disciplinas/**").hasRole("PROFESSOR")
                                                .requestMatchers(HttpMethod.DELETE, "/api/disciplinas/**").hasRole("PROFESSOR")
                                                .requestMatchers(HttpMethod.POST, "/api/conteudos", "/api/conteudos/**").hasRole("PROFESSOR")
                                                .requestMatchers(HttpMethod.PUT, "/api/conteudos/**").hasRole("PROFESSOR")
                                                .requestMatchers(HttpMethod.PATCH, "/api/conteudos/**").hasRole("PROFESSOR")
                                                .requestMatchers(HttpMethod.DELETE, "/api/conteudos/**").hasRole("PROFESSOR")
                                                // Só PROFESSOR corrige e vê as entregas de uma atividade
                                                .requestMatchers(HttpMethod.GET, "/api/usuarios-tarefas/atividade/**").hasRole("PROFESSOR")
                                                .requestMatchers(HttpMethod.PATCH, "/api/usuarios-tarefas/*/concluir").hasRole("PROFESSOR")
                                                .requestMatchers(HttpMethod.PUT, "/api/usuarios-tarefas/**").hasRole("PROFESSOR")
                                                .requestMatchers(HttpMethod.DELETE, "/api/usuarios-tarefas/**").hasRole("PROFESSOR")
                                                // Só ALUNO entra em turma, pega e entrega atividade
                                                .requestMatchers(HttpMethod.POST, "/api/usuarios-turmas").hasRole("ALUNO")
                                                .requestMatchers(HttpMethod.POST, "/api/usuarios-tarefas", "/api/usuarios-tarefas/**").hasRole("ALUNO")

                                                // 🟢 Qualquer outra rota exigirá um token JWT válido
                                                .anyRequest().authenticated())
                                .exceptionHandling(ex -> ex
                                                .authenticationEntryPoint((req, res, e) -> {
                                                        res.setStatus(401);
                                                        res.setContentType("application/json;charset=UTF-8");
                                                        res.getWriter().write("{\"status\":401,\"erro\":\"Não autenticado: token ausente ou inválido\"}");
                                                })
                                                .accessDeniedHandler((req, res, e) -> {
                                                        res.setStatus(403);
                                                        res.setContentType("application/json;charset=UTF-8");
                                                        res.getWriter().write("{\"status\":403,\"erro\":\"Acesso negado: seu perfil não tem permissão para este recurso\"}");
                                                }))
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