package br.cefetmg.grow.controller;

import br.cefetmg.grow.dto.EspecieFavoritaRequestDTO;
import br.cefetmg.grow.dto.EspecieFavoritaResponseDTO;
import br.cefetmg.grow.service.EspecieFavoritaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/favoritos")
@RequiredArgsConstructor
@Tag(name = "Favoritos", description = "Endpoints para gerenciar favoritos de espécies de plantas")
public class EspecieFavoritaController {

    private final EspecieFavoritaService favoritoService;

    @GetMapping
    @Operation(summary = "Listar todos os favoritos")
    public ResponseEntity<List<EspecieFavoritaResponseDTO>> listarTodos() {
        return ResponseEntity.ok(favoritoService.listarTodos());
    }

    @GetMapping("/usuario/{usuarioId}")
    @Operation(summary = "Listar favoritos de um usuário específico")
    public ResponseEntity<List<EspecieFavoritaResponseDTO>> listarPorUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(favoritoService.listarPorUsuario(usuarioId));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar favorito por ID")
    public ResponseEntity<EspecieFavoritaResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(favoritoService.buscarPorId(id));
    }

    @PostMapping
    @Operation(summary = "Favoritar uma espécie de planta")
    public ResponseEntity<EspecieFavoritaResponseDTO> favoritar(@Valid @RequestBody EspecieFavoritaRequestDTO dto) {
        EspecieFavoritaResponseDTO criado = favoritoService.favoritar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(criado);
    }

    @DeleteMapping("/usuario/{usuarioId}/especie/{especieId}")
    @Operation(summary = "Desfavoritar uma espécie de planta")
    public ResponseEntity<Void> desfavoritar(@PathVariable Long usuarioId, @PathVariable Long especieId) {
        favoritoService.desfavoritar(usuarioId, especieId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir um favorito por ID")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        favoritoService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}