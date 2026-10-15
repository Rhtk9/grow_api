package br.cefetmg.grow.security;

import java.io.IOException;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import br.cefetmg.grow.model.Usuario;
import br.cefetmg.grow.repository.UsuarioRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UsuarioRepository usuarioRepository;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();
        String method = request.getMethod();

        return "OPTIONS".equalsIgnoreCase(method)
                || path.startsWith("/auth/")
                || path.startsWith("/api/auth/")
                || path.startsWith("/v3/api-docs")
                || path.startsWith("/swagger-ui");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain) throws ServletException, IOException {
        String header = request.getHeader("Authorization");

        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);
            
            try {
                if (jwtUtil.tokenValido(token)) {
                    String email = jwtUtil.extrairEmail(token);
                    Usuario usuario = usuarioRepository.findByEmail(email).orElse(null);
                    
                    if (usuario != null) {
                        List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_USER"));
                        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                                usuario, null, authorities
                        );
                        SecurityContextHolder.getContext().setAuthentication(auth);
                        System.out.println(">>> [JWT SUCCESS] Usuario autenticado: " + email);
                    } else {
                        System.out.println(">>> [JWT ERROR] Usuario NAO encontrado no banco para o e-mail: " + email);
                    }
                } else {
                    System.out.println(">>> [JWT ERROR] Método tokenValido() retornou FALSE para o token enviado.");
                }
            } catch (Exception e) {
                System.out.println(">>> [JWT ERROR] Exceção ao validar token: " + e.getMessage());
            }
        } else {
            System.out.println(">>> [JWT WARN] Nenhum cabeçalho 'Authorization: Bearer' encontrado na requisição.");
        }

        chain.doFilter(request, response);
    }
}