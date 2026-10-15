package br.cefetmg.grow.service;

import br.cefetmg.grow.model.Usuario;
import br.cefetmg.grow.repository.UsuarioRepository;
import br.cefetmg.grow.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final JwtUtil jwtUtil;

    public String autenticar(String email, String senha) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Credenciais inválidas"));
        if (!usuario.getSenha().equals(senha)) {
            throw new RuntimeException("Credenciais inválidas");
        }
        return jwtUtil.gerarToken(usuario.getEmail());
    }
}