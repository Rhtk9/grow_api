package br.cefetmg.grow.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.cefetmg.grow.dto.AvaliacaoRequestDTO;
import br.cefetmg.grow.dto.AvaliacaoResponseDTO;
import br.cefetmg.grow.service.AvaliacaoService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/avaliacoes")
@CrossOrigin(origins = "*")
public class AvaliacaoController {

    @Autowired
    private AvaliacaoService avaliacaoService;

    @GetMapping("/especie/{especieId}")
    public ResponseEntity<List<AvaliacaoResponseDTO>> listarPorEspecie(@PathVariable Long especieId) {
        List<AvaliacaoResponseDTO> avaliacoes = avaliacaoService.listarPorEspecie(especieId);
        return ResponseEntity.ok(avaliacoes);
    }

    @PostMapping
    public ResponseEntity<AvaliacaoResponseDTO> criar(@Valid @RequestBody AvaliacaoRequestDTO dto) {
        AvaliacaoResponseDTO novaAvaliacao = avaliacaoService.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaAvaliacao);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        avaliacaoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}