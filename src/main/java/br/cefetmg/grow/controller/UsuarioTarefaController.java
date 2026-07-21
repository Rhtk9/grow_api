package br.cefetmg.grow.controller;

import br.cefetmg.grow.dto.UsuarioTarefaRequestDTO;
import br.cefetmg.grow.dto.UsuarioTarefaResponseDTO;
import br.cefetmg.grow.service.UsuarioTarefaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/usuarios-tarefas")
@RequiredArgsConstructor
public class UsuarioTarefaController {

    private final UsuarioTarefaService usuarioTarefaService;

    @GetMapping
    public ResponseEntity<List<UsuarioTarefaResponseDTO>> listarTodos() {
        return ResponseEntity.ok(usuarioTarefaService.listarTodos());
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<UsuarioTarefaResponseDTO>> listarPorUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(usuarioTarefaService.listarPorUsuario(usuarioId));
    }

    @GetMapping("/atividade/{atividadeId}")
    public ResponseEntity<List<UsuarioTarefaResponseDTO>> listarPorAtividade(@PathVariable Long atividadeId) {
        return ResponseEntity.ok(usuarioTarefaService.listarPorAtividade(atividadeId));
    }

    @GetMapping("/concluidas")
    public ResponseEntity<List<UsuarioTarefaResponseDTO>> listarConcluidas() {
        return ResponseEntity.ok(usuarioTarefaService.listarConcluidas());
    }

    @GetMapping("/nao-concluidas")
    public ResponseEntity<List<UsuarioTarefaResponseDTO>> listarNaoConcluidas() {
        return ResponseEntity.ok(usuarioTarefaService.listarNaoConcluidas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioTarefaResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioTarefaService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<UsuarioTarefaResponseDTO> criar(@Valid @RequestBody UsuarioTarefaRequestDTO dto) {
        UsuarioTarefaResponseDTO criado = usuarioTarefaService.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(criado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioTarefaResponseDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody UsuarioTarefaRequestDTO dto) {
        return ResponseEntity.ok(usuarioTarefaService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        usuarioTarefaService.excluir(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/concluir")
    public ResponseEntity<UsuarioTarefaResponseDTO> concluirTarefa(
            @PathVariable Long id,
            @RequestParam BigDecimal nota,
            @RequestParam(required = false) String feedback) {
        return ResponseEntity.ok(usuarioTarefaService.concluirTarefa(id, nota, feedback));
    }
}