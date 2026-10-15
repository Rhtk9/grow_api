package br.cefetmg.grow.controller;

import br.cefetmg.grow.dto.UsuarioRecompensaRequestDTO;
import br.cefetmg.grow.dto.UsuarioRecompensaResponseDTO;
import br.cefetmg.grow.service.UsuarioRecompensaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios-recompensas")
@RequiredArgsConstructor
public class UsuarioRecompensaController {

    private final UsuarioRecompensaService usuarioRecompensaService;

    @GetMapping
    public ResponseEntity<List<UsuarioRecompensaResponseDTO>> listarTodos() {
        return ResponseEntity.ok(usuarioRecompensaService.listarTodos());
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<UsuarioRecompensaResponseDTO>> listarPorUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(usuarioRecompensaService.listarPorUsuario(usuarioId));
    }

    @GetMapping("/usuario/{usuarioId}/equipadas")
    public ResponseEntity<List<UsuarioRecompensaResponseDTO>> listarEquipadasPorUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(usuarioRecompensaService.listarEquipadasPorUsuario(usuarioId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioRecompensaResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioRecompensaService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<UsuarioRecompensaResponseDTO> desbloquear(@Valid @RequestBody UsuarioRecompensaRequestDTO dto) {
        UsuarioRecompensaResponseDTO criado = usuarioRecompensaService.desbloquear(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(criado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        usuarioRecompensaService.excluir(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/equipar")
    public ResponseEntity<UsuarioRecompensaResponseDTO> equipar(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioRecompensaService.equipar(id));
    }

    @PatchMapping("/{id}/desequipar")
    public ResponseEntity<UsuarioRecompensaResponseDTO> desequipar(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioRecompensaService.desequipar(id));
    }
}