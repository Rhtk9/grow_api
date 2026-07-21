package br.cefetmg.grow.controller;

import br.cefetmg.grow.dto.ConteudoRequestDTO;
import br.cefetmg.grow.dto.ConteudoResponseDTO;
import br.cefetmg.grow.service.ConteudoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/conteudos")
@RequiredArgsConstructor
public class ConteudoController {

    private final ConteudoService conteudoService;

    @GetMapping
    public ResponseEntity<List<ConteudoResponseDTO>> listarTodos() {
        return ResponseEntity.ok(conteudoService.listarTodos());
    }

    @GetMapping("/publicados")
    public ResponseEntity<List<ConteudoResponseDTO>> listarPublicados() {
        return ResponseEntity.ok(conteudoService.listarPublicados());
    }

    @GetMapping("/disciplina/{disciplinaId}")
    public ResponseEntity<List<ConteudoResponseDTO>> listarPorDisciplina(@PathVariable Long disciplinaId) {
        return ResponseEntity.ok(conteudoService.listarPorDisciplina(disciplinaId));
    }

    @GetMapping("/disciplina/{disciplinaId}/publicados")
    public ResponseEntity<List<ConteudoResponseDTO>> listarPublicadosPorDisciplina(@PathVariable Long disciplinaId) {
        return ResponseEntity.ok(conteudoService.listarPublicadosPorDisciplina(disciplinaId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConteudoResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(conteudoService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<ConteudoResponseDTO> criar(@Valid @RequestBody ConteudoRequestDTO dto) {
        ConteudoResponseDTO criado = conteudoService.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(criado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ConteudoResponseDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody ConteudoRequestDTO dto) {
        return ResponseEntity.ok(conteudoService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        conteudoService.excluir(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/publicar")
    public ResponseEntity<ConteudoResponseDTO> publicar(@PathVariable Long id) {
        return ResponseEntity.ok(conteudoService.publicar(id));
    }

    @PatchMapping("/{id}/despublicar")
    public ResponseEntity<ConteudoResponseDTO> despublicar(@PathVariable Long id) {
        return ResponseEntity.ok(conteudoService.despublicar(id));
    }
}