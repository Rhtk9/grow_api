package br.cefetmg.grow.controller;

import br.cefetmg.grow.dto.UsuarioResponseDTO;
import br.cefetmg.grow.service.AuthService;
import br.cefetmg.grow.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

// br.cefetmg.grow.controller.AuthController.java
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UsuarioService usuarioService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credenciais) {
        String email = credenciais.get("email");
        String senha = credenciais.get("senha");
        try {
            String token = authService.autenticar(email, senha);
            UsuarioResponseDTO usuario = usuarioService.buscarPorEmail(email);
            return ResponseEntity.ok(Map.of("token", token, "usuario", usuario));
        } catch (RuntimeException e) {
            return ResponseEntity.status(401).body(Map.of("erro", e.getMessage()));
        }
    }
}