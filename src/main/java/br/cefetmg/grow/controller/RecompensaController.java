package br.cefetmg.grow.controller;

import br.cefetmg.grow.dto.RecompensaRequestDTO;
import br.cefetmg.grow.dto.RecompensaResponseDTO;
import br.cefetmg.grow.service.RecompensaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recompensas")
@RequiredArgsConstructor
public class RecompensaController {

    private final RecompensaService recompensaService;

    @GetMapping
    public ResponseEntity<List<RecompensaResponseDTO>> listarTodas() {
        return ResponseEntity.ok(recompensaService.listarTodas());
    }

    @GetMapping("/raridade/{raridade}")
    public ResponseEntity<List<RecompensaResponseDTO>> listarPorRaridade(@PathVariable String raridade) {
        return ResponseEntity.ok(recompensaService.listarPorRaridade(raridade));
    }

    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<RecompensaResponseDTO>> listarPorTipo(@PathVariable String tipo) {
        return ResponseEntity.ok(recompensaService.listarPorTipo(tipo));
    }

    @GetMapping("/disponiveis")
    public ResponseEntity<List<RecompensaResponseDTO>> listarDisponiveis(@RequestParam Integer xpUsuario) {
        return ResponseEntity.ok(recompensaService.listarDisponiveis(xpUsuario));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecompensaResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(recompensaService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<RecompensaResponseDTO> criar(@Valid @RequestBody RecompensaRequestDTO dto) {
        RecompensaResponseDTO criada = recompensaService.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(criada);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RecompensaResponseDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody RecompensaRequestDTO dto) {
        return ResponseEntity.ok(recompensaService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        recompensaService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}