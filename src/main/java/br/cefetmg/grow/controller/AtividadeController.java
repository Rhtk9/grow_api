package br.cefetmg.grow.controller;

import br.cefetmg.grow.dto.AtividadeRequestDTO;
import br.cefetmg.grow.dto.AtividadeResponseDTO;
import br.cefetmg.grow.service.AtividadeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/atividades")
@RequiredArgsConstructor
public class AtividadeController {

    private final AtividadeService atividadeService;

    @GetMapping
    public ResponseEntity<List<AtividadeResponseDTO>> listarTodas() {
        return ResponseEntity.ok(atividadeService.listarTodas());
    }

    @GetMapping("/turma/{turmaId}")
    public ResponseEntity<List<AtividadeResponseDTO>> listarPorTurma(@PathVariable Long turmaId) {
        return ResponseEntity.ok(atividadeService.listarPorTurma(turmaId));
    }

    @GetMapping("/turma/{turmaId}/futuras")
    public ResponseEntity<List<AtividadeResponseDTO>> listarFuturasPorTurma(@PathVariable Long turmaId) {
        return ResponseEntity.ok(atividadeService.listarAtividadesFuturasPorTurma(turmaId));
    }

    @GetMapping("/turma/{turmaId}/passadas")
    public ResponseEntity<List<AtividadeResponseDTO>> listarPassadasPorTurma(@PathVariable Long turmaId) {
        return ResponseEntity.ok(atividadeService.listarAtividadesPassadasPorTurma(turmaId));
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<AtividadeResponseDTO>> listarPorUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(atividadeService.listarPorUsuario(usuarioId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AtividadeResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(atividadeService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<AtividadeResponseDTO> criar(@Valid @RequestBody AtividadeRequestDTO dto) {
        AtividadeResponseDTO criada = atividadeService.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(criada);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AtividadeResponseDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody AtividadeRequestDTO dto) {
        return ResponseEntity.ok(atividadeService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        atividadeService.excluir(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<AtividadeResponseDTO> atualizarStatus(
            @PathVariable Long id,
            @RequestParam String status) {
        return ResponseEntity.ok(atividadeService.atualizarStatus(id, status));
    }
}