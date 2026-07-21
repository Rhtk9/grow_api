package br.cefetmg.grow.controller;

import br.cefetmg.grow.dto.ResponsavelRequestDTO;
import br.cefetmg.grow.dto.ResponsavelResponseDTO;
import br.cefetmg.grow.service.ResponsavelService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/responsaveis")
@RequiredArgsConstructor
public class ResponsavelController {

    private final ResponsavelService responsavelService;

    @GetMapping
    public ResponseEntity<List<ResponsavelResponseDTO>> listarTodos() {
        return ResponseEntity.ok(responsavelService.listarTodos());
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<ResponsavelResponseDTO>> listarPorUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(responsavelService.listarPorUsuario(usuarioId));
    }

    @GetMapping("/notificacoes-ativas")
    public ResponseEntity<List<ResponsavelResponseDTO>> listarComNotificacoesAtivas() {
        return ResponseEntity.ok(responsavelService.listarComNotificacoesAtivas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponsavelResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(responsavelService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<ResponsavelResponseDTO> criar(@Valid @RequestBody ResponsavelRequestDTO dto) {
        ResponsavelResponseDTO criado = responsavelService.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(criado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponsavelResponseDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody ResponsavelRequestDTO dto) {
        return ResponseEntity.ok(responsavelService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        responsavelService.excluir(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/notificacoes")
    public ResponseEntity<ResponsavelResponseDTO> atualizarNotificacoes(
            @PathVariable Long id,
            @RequestParam Boolean notificacoes) {
        return ResponseEntity.ok(responsavelService.atualizarNotificacoes(id, notificacoes));
    }
}