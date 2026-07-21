package br.cefetmg.grow.controller;

import br.cefetmg.grow.dto.RegistroRequestDTO;
import br.cefetmg.grow.dto.RegistroResponseDTO;
import br.cefetmg.grow.service.RegistroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/registros")
@RequiredArgsConstructor
public class RegistroController {

    private final RegistroService registroService;

    @GetMapping
    public ResponseEntity<List<RegistroResponseDTO>> listarTodos() {
        return ResponseEntity.ok(registroService.listarTodos());
    }

    @GetMapping("/planta/{plantaUsuarioId}")
    public ResponseEntity<List<RegistroResponseDTO>> listarPorPlanta(@PathVariable Long plantaUsuarioId) {
        return ResponseEntity.ok(registroService.listarPorPlanta(plantaUsuarioId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RegistroResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(registroService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<RegistroResponseDTO> criar(@Valid @RequestBody RegistroRequestDTO dto) {
        RegistroResponseDTO criado = registroService.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(criado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RegistroResponseDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody RegistroRequestDTO dto) {
        return ResponseEntity.ok(registroService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        registroService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}