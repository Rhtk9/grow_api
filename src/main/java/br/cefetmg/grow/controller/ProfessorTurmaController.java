package br.cefetmg.grow.controller;

import br.cefetmg.grow.dto.ProfessorTurmaRequestDTO;
import br.cefetmg.grow.dto.ProfessorTurmaResponseDTO;
import br.cefetmg.grow.service.ProfessorTurmaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/professores-turmas")
@RequiredArgsConstructor
public class ProfessorTurmaController {

    private final ProfessorTurmaService professorTurmaService;

    @GetMapping
    public ResponseEntity<List<ProfessorTurmaResponseDTO>> listarTodos() {
        return ResponseEntity.ok(professorTurmaService.listarTodos());
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<ProfessorTurmaResponseDTO>> listarPorUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(professorTurmaService.listarPorUsuario(usuarioId));
    }

    @GetMapping("/turma/{turmaId}")
    public ResponseEntity<List<ProfessorTurmaResponseDTO>> listarPorTurma(@PathVariable Long turmaId) {
        return ResponseEntity.ok(professorTurmaService.listarPorTurma(turmaId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfessorTurmaResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(professorTurmaService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<ProfessorTurmaResponseDTO> criar(@Valid @RequestBody ProfessorTurmaRequestDTO dto) {
        ProfessorTurmaResponseDTO criado = professorTurmaService.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(criado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProfessorTurmaResponseDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody ProfessorTurmaRequestDTO dto) {
        return ResponseEntity.ok(professorTurmaService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        professorTurmaService.excluir(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/permissoes")
    public ResponseEntity<ProfessorTurmaResponseDTO> atualizarPermissoes(
            @PathVariable Long id,
            @RequestParam(required = false) Boolean excluir,
            @RequestParam(required = false) Boolean edicao,
            @RequestParam(required = false) Boolean gerenciarTarefas) {
        return ResponseEntity.ok(professorTurmaService.atualizarPermissoes(id, excluir, edicao, gerenciarTarefas));
    }
}