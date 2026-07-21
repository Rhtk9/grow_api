package br.cefetmg.grow.controller;

import br.cefetmg.grow.dto.DisciplinaRequestDTO;
import br.cefetmg.grow.dto.DisciplinaResponseDTO;
import br.cefetmg.grow.service.DisciplinaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/disciplinas")
@RequiredArgsConstructor
public class DisciplinaController {

    private final DisciplinaService disciplinaService;

    @GetMapping
    public ResponseEntity<List<DisciplinaResponseDTO>> listarTodas() {
        return ResponseEntity.ok(disciplinaService.listarTodas());
    }

    @GetMapping("/ativas")
    public ResponseEntity<List<DisciplinaResponseDTO>> listarAtivas() {
        return ResponseEntity.ok(disciplinaService.listarAtivas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DisciplinaResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(disciplinaService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<DisciplinaResponseDTO> criar(@Valid @RequestBody DisciplinaRequestDTO dto) {
        DisciplinaResponseDTO criada = disciplinaService.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(criada);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DisciplinaResponseDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody DisciplinaRequestDTO dto) {
        return ResponseEntity.ok(disciplinaService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        disciplinaService.excluir(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/ativar")
    public ResponseEntity<DisciplinaResponseDTO> ativar(@PathVariable Long id) {
        return ResponseEntity.ok(disciplinaService.ativarDesativar(id, true));
    }

    @PatchMapping("/{id}/desativar")
    public ResponseEntity<DisciplinaResponseDTO> desativar(@PathVariable Long id) {
        return ResponseEntity.ok(disciplinaService.ativarDesativar(id, false));
    }
}