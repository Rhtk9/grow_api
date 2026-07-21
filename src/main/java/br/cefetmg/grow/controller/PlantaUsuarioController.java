package br.cefetmg.grow.controller;

import br.cefetmg.grow.dto.PlantaUsuarioRequestDTO;
import br.cefetmg.grow.dto.PlantaUsuarioResponseDTO;
import br.cefetmg.grow.service.PlantaUsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/plantas-usuario")
@RequiredArgsConstructor
@Tag(name = "Plantas do Usuário", description = "Endpoints para gerenciar plantas dos usuários")
public class PlantaUsuarioController {

    private final PlantaUsuarioService plantaService;

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
    @Operation(summary = "Atualizar uma planta do usuário")
    public ResponseEntity<PlantaUsuarioResponseDTO> atualizar(@PathVariable Long id,
                                                               @Valid @RequestBody PlantaUsuarioRequestDTO dto) {
        return ResponseEntity.ok(plantaService.atualizar(id, dto));
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Atualizar felicidade e saúde da planta")
    public ResponseEntity<PlantaUsuarioResponseDTO> atualizarStatus(@PathVariable Long id,
                                                                     @RequestParam(required = false) Integer felicidade,
                                                                     @RequestParam(required = false) Integer saude) {
        return ResponseEntity.ok(plantaService.atualizarStatus(id, felicidade, saude));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir uma planta do usuário")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        plantaService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}