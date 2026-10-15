package br.cefetmg.grow.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.cefetmg.grow.dto.EvolucaoFaseRequestDTO;
import br.cefetmg.grow.dto.EvolucaoFaseResponseDTO;
import br.cefetmg.grow.service.EvolucaoFaseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/evolucoes-fase")
@RequiredArgsConstructor
@Tag(name = "Evoluções de Fase", description = "Endpoints para gerenciamento de parâmetros de evolução das fases")
public class EvolucaoFaseController {

    private final EvolucaoFaseService evolucaoFaseService;

    @GetMapping
    @Operation(summary = "Listar todas as regras de evolução de fase")
    public ResponseEntity<List<EvolucaoFaseResponseDTO>> listarTodos() {
        return ResponseEntity.ok(evolucaoFaseService.listarTodos());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar regra de evolução de fase por ID")
    public ResponseEntity<EvolucaoFaseResponseDTO> buscarPorId(
            @Parameter(description = "ID da evolução de fase", required = true) @PathVariable Long id) {
        return ResponseEntity.ok(evolucaoFaseService.buscarPorId(id));
    }

    @PostMapping
    @Operation(summary = "Cadastrar nova regra de evolução de fase")
    public ResponseEntity<EvolucaoFaseResponseDTO> criar(@Valid @RequestBody EvolucaoFaseRequestDTO dto) {
        EvolucaoFaseResponseDTO criada = evolucaoFaseService.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(criada);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar regra de evolução de fase existente")
    public ResponseEntity<EvolucaoFaseResponseDTO> atualizar(
            @Parameter(description = "ID da evolução de fase", required = true) @PathVariable Long id,
            @Valid @RequestBody EvolucaoFaseRequestDTO dto) {
        return ResponseEntity.ok(evolucaoFaseService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir regra de evolução de fase")
    public ResponseEntity<Void> excluir(
            @Parameter(description = "ID da evolução de fase", required = true) @PathVariable Long id) {
        evolucaoFaseService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}