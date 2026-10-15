package br.cefetmg.grow.controller;

import br.cefetmg.grow.dto.RelatorioRequestDTO;
import br.cefetmg.grow.dto.RelatorioResponseDTO;
import br.cefetmg.grow.service.RelatorioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/relatorios")
@RequiredArgsConstructor
public class RelatorioController {

    private final RelatorioService relatorioService;

    @GetMapping
    public ResponseEntity<List<RelatorioResponseDTO>> listarTodos() {
        return ResponseEntity.ok(relatorioService.listarTodos());
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<RelatorioResponseDTO>> listarPorUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(relatorioService.listarPorUsuario(usuarioId));
    }

    @GetMapping("/usuario/{usuarioId}/ultimo")
    public ResponseEntity<RelatorioResponseDTO> buscarUltimoPorUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(relatorioService.buscarUltimoPorUsuario(usuarioId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RelatorioResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(relatorioService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<RelatorioResponseDTO> criar(@Valid @RequestBody RelatorioRequestDTO dto) {
        RelatorioResponseDTO criado = relatorioService.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(criado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RelatorioResponseDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody RelatorioRequestDTO dto) {
        return ResponseEntity.ok(relatorioService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        relatorioService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}