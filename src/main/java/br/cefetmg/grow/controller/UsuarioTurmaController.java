package br.cefetmg.grow.controller;

import br.cefetmg.grow.dto.UsuarioTurmaRequestDTO;
import br.cefetmg.grow.dto.UsuarioTurmaResponseDTO;
import br.cefetmg.grow.service.UsuarioTurmaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios-turmas")
@RequiredArgsConstructor
public class UsuarioTurmaController {

    private final UsuarioTurmaService usuarioTurmaService;

    @GetMapping
    public ResponseEntity<List<UsuarioTurmaResponseDTO>> listarTodos() {
        return ResponseEntity.ok(usuarioTurmaService.listarTodos());
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<UsuarioTurmaResponseDTO>> listarPorUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(usuarioTurmaService.listarPorUsuario(usuarioId));
    }

    @GetMapping("/turma/{turmaId}")
    public ResponseEntity<List<UsuarioTurmaResponseDTO>> listarPorTurma(@PathVariable Long turmaId) {
        return ResponseEntity.ok(usuarioTurmaService.listarPorTurma(turmaId));
    }

    @GetMapping("/ativos")
    public ResponseEntity<List<UsuarioTurmaResponseDTO>> listarAtivos() {
        return ResponseEntity.ok(usuarioTurmaService.listarAtivos());
    }

    @GetMapping("/favoritos")
    public ResponseEntity<List<UsuarioTurmaResponseDTO>> listarFavoritos() {
        return ResponseEntity.ok(usuarioTurmaService.listarFavoritos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioTurmaResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioTurmaService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<UsuarioTurmaResponseDTO> criar(@Valid @RequestBody UsuarioTurmaRequestDTO dto) {
        UsuarioTurmaResponseDTO criado = usuarioTurmaService.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(criado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioTurmaResponseDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody UsuarioTurmaRequestDTO dto) {
        return ResponseEntity.ok(usuarioTurmaService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        usuarioTurmaService.excluir(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/ativar")
    public ResponseEntity<UsuarioTurmaResponseDTO> ativar(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioTurmaService.ativarDesativar(id, true));
    }

    @PatchMapping("/{id}/desativar")
    public ResponseEntity<UsuarioTurmaResponseDTO> desativar(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioTurmaService.ativarDesativar(id, false));
    }

    @PatchMapping("/{id}/favoritar")
    public ResponseEntity<UsuarioTurmaResponseDTO> favoritar(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioTurmaService.favoritarDesfavoritar(id, true));
    }

    @PatchMapping("/{id}/desfavoritar")
    public ResponseEntity<UsuarioTurmaResponseDTO> desfavoritar(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioTurmaService.favoritarDesfavoritar(id, false));
    }
}