package br.cefetmg.grow.controller;

import br.cefetmg.grow.dto.FasePlantaRequestDTO;
import br.cefetmg.grow.dto.FasePlantaResponseDTO;
import br.cefetmg.grow.service.FasePlantaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fases")
@RequiredArgsConstructor
@Tag(name = "Fases de Plantas", description = "Endpoints para gerenciamento de fases de plantas")
public class FasePlantaController {

    private final FasePlantaService fasePlantaService;

    @GetMapping
    @Operation(summary = "Listar todas as fases")
    public ResponseEntity<List<FasePlantaResponseDTO>> listarTodas() {
        return ResponseEntity.ok(fasePlantaService.listarTodas());
    }

    @GetMapping("/especie/{especieId}")
    @Operation(summary = "Listar fases por espécie")
    public ResponseEntity<List<FasePlantaResponseDTO>> listarPorEspecie(
            @Parameter(description = "ID da espécie") @PathVariable Long especieId) {
        return ResponseEntity.ok(fasePlantaService.listarPorEspecie(especieId));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar fase por ID")
    public ResponseEntity<FasePlantaResponseDTO> buscarPorId(
            @Parameter(description = "ID da fase") @PathVariable Long id) {
        return ResponseEntity.ok(fasePlantaService.buscarPorId(id));
    }

    @PostMapping
    @Operation(summary = "Cadastrar nova fase")
    public ResponseEntity<FasePlantaResponseDTO> inserir(@Valid @RequestBody FasePlantaRequestDTO dto) {
        FasePlantaResponseDTO criada = fasePlantaService.inserir(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(criada);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar fase")
    public ResponseEntity<FasePlantaResponseDTO> atualizar(
            @Parameter(description = "ID da fase") @PathVariable Long id,
            @Valid @RequestBody FasePlantaRequestDTO dto) {
        return ResponseEntity.ok(fasePlantaService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir fase")
    public ResponseEntity<Void> excluir(@Parameter(description = "ID da fase") @PathVariable Long id) {
        fasePlantaService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}