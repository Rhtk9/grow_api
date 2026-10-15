package br.cefetmg.grow.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.cefetmg.grow.dto.PlantaUsuarioRequestDTO;
import br.cefetmg.grow.dto.PlantaUsuarioResponseDTO;
import br.cefetmg.grow.service.PlantaUsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/plantas-usuario")
@RequiredArgsConstructor
@Tag(name = "Plantas do Usuário", description = "Endpoints para gerenciar plantas dos usuários e atualizar seus estados dinâmicos")
public class PlantaUsuarioController {

    private final PlantaUsuarioService plantaService;

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<String> handleIllegalState(IllegalStateException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @GetMapping
    @Operation(summary = "Listar todas as plantas dos usuários")
    public ResponseEntity<List<PlantaUsuarioResponseDTO>> listarTodos() {
        return ResponseEntity.ok(plantaService.listarTodos());
    }

    @GetMapping("/usuario/{usuarioId}")
    @Operation(summary = "Listar plantas de um usuário específico")
    public ResponseEntity<List<PlantaUsuarioResponseDTO>> listarPorUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(plantaService.listarPorUsuario(usuarioId));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar planta do usuário por ID")
    public ResponseEntity<PlantaUsuarioResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(plantaService.buscarPorId(id));
    }

    @PostMapping
    @Operation(summary = "Criar uma nova planta para o usuário")
    public ResponseEntity<PlantaUsuarioResponseDTO> criar(@Valid @RequestBody PlantaUsuarioRequestDTO dto) {
        PlantaUsuarioResponseDTO criado = plantaService.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(criado);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar informações cadastrais de uma planta do usuário")
    public ResponseEntity<PlantaUsuarioResponseDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody PlantaUsuarioRequestDTO dto) {
        return ResponseEntity.ok(plantaService.atualizar(id, dto));
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Atualizar indicadores de saúde, felicidade e recursos acumulados da planta")
    public ResponseEntity<PlantaUsuarioResponseDTO> atualizarStatus(
            @PathVariable Long id,
            @RequestParam(required = false) Integer felicidade,
            @RequestParam(required = false) Integer saude,
            @RequestParam(required = false) Integer aguaAtual,
            @RequestParam(required = false) Integer nutrientesAtual,
            @RequestParam(required = false) Integer luzAtual,
            @RequestParam(required = false) Integer estresse) {
        return ResponseEntity.ok(
                plantaService.atualizarStatus(id, felicidade, saude, aguaAtual, nutrientesAtual, luzAtual, estresse));
    }

    @PatchMapping("/{id}/posicao")
    @Operation(summary = "Alterar a posição de exposição solar atual da planta (ex: SOL_PLENO, MEIA_SOMBRA, SOMBRA)")
    public ResponseEntity<PlantaUsuarioResponseDTO> alterarPosicao(
            @PathVariable Long id,
            @RequestParam String posicao) {
        return ResponseEntity.ok(plantaService.alterarPosicao(id, posicao));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir uma planta do usuário")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        plantaService.excluir(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/regar")
    @Operation(summary = "Regar a planta (+45 de água, cooldown 6h)")
    public ResponseEntity<PlantaUsuarioResponseDTO> regar(@PathVariable Long id) {
        return ResponseEntity.ok(plantaService.regar(id));
    }

    @PostMapping("/{id}/adubar")
    @Operation(summary = "Adubar a planta (+60 de nutrientes)")
    public ResponseEntity<PlantaUsuarioResponseDTO> adubar(@PathVariable Long id) {
        return ResponseEntity.ok(plantaService.adubar(id));
    }

    @PostMapping("/{id}/mover")
    @Operation(summary = "Mover a planta para outra posição (SOL_PLENO, MEIA_SOMBRA, SOMBRA)")
    public ResponseEntity<PlantaUsuarioResponseDTO> mover(
            @PathVariable Long id,
            @RequestParam String posicao) {
        return ResponseEntity.ok(plantaService.mover(id, posicao));
    }
}