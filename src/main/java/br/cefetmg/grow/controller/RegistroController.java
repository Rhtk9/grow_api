package br.cefetmg.grow.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.cefetmg.grow.dto.RegistroRequestDTO;
import br.cefetmg.grow.dto.RegistroResponseDTO;
import br.cefetmg.grow.service.RegistroService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/registros")
@RequiredArgsConstructor
@Tag(name = "Registros", description = "Endpoints para registro de ações e cuidados com as plantas")
public class RegistroController {

    private final RegistroService registroService;

    @GetMapping
    @Operation(summary = "Listar todos os registros")
    public ResponseEntity<List<RegistroResponseDTO>> listarTodos() {
        return ResponseEntity.ok(registroService.listarTodos());
    }

    @GetMapping("/planta/{plantaUsuarioId}")
    @Operation(summary = "Listar registros por planta do usuário")
    public ResponseEntity<List<RegistroResponseDTO>> listarPorPlanta(
            @Parameter(description = "ID da planta do usuário") @PathVariable Long plantaUsuarioId) {
        return ResponseEntity.ok(registroService.listarPorPlanta(plantaUsuarioId));
    }

    @GetMapping("/periodo")
    @Operation(summary = "Listar registros por intervalo de datas")
    public ResponseEntity<List<RegistroResponseDTO>> listarPorPeriodo(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fim) {
        return ResponseEntity.ok(registroService.listarPorPeriodo(inicio, fim));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar registro por ID")
    public ResponseEntity<RegistroResponseDTO> buscarPorId(
            @Parameter(description = "ID do registro") @PathVariable Long id) {
        return ResponseEntity.ok(registroService.buscarPorId(id));
    }

    @PostMapping
    @Operation(summary = "Criar novo registro de cuidado")
    public ResponseEntity<RegistroResponseDTO> criar(@Valid @RequestBody RegistroRequestDTO dto) {
        RegistroResponseDTO criado = registroService.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(criado);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar registro existente")
    public ResponseEntity<RegistroResponseDTO> atualizar(
            @Parameter(description = "ID do registro") @PathVariable Long id,
            @Valid @RequestBody RegistroRequestDTO dto) {
        return ResponseEntity.ok(registroService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir registro")
    public ResponseEntity<Void> excluir(@Parameter(description = "ID do registro") @PathVariable Long id) {
        registroService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}