package br.cefetmg.grow.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.cefetmg.grow.dto.AvaliacaoRequestDTO;
import br.cefetmg.grow.dto.AvaliacaoResponseDTO;
import br.cefetmg.grow.model.Avaliacao;
import br.cefetmg.grow.model.EspeciePlanta;
import br.cefetmg.grow.model.Usuario;
import br.cefetmg.grow.repository.AvaliacaoRepository;
import br.cefetmg.grow.repository.EspeciePlantaRepository;
import br.cefetmg.grow.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class AvaliacaoService {

    @Autowired
    private AvaliacaoRepository avaliacaoRepository;

    @Autowired
    private EspeciePlantaRepository especiePlantaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<AvaliacaoResponseDTO> listarPorEspecie(Long especieId) {
        return avaliacaoRepository.findByEspecieCdEspecie(especieId)
                .stream()
                .map(this.converterParaDTO)
                .collect(Collectors.toList());
    }

    public AvaliacaoResponseDTO criar(AvaliacaoRequestDTO dto) {
        EspeciePlanta especie = especiePlantaRepository.findById(dto.getEspecieId())
                .orElseThrow(() -> new EntityNotFoundException("Espécie não encontrada"));

        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

        // Regra opcional: Se o usuário já avaliou esta espécie, atualiza em vez de duplicar
        Avaliacao avaliacao = avaliacaoRepository
                .findByEspecieCdEspecieAndUsuarioCdUsuario(dto.getEspecieId(), dto.getUsuarioId())
                .orElse(new Avaliacao());

        avaliacao.setNota(dto.getNota());
        avaliacao.setComentario(dto.getComentario());
        avaliacao.setDataAvaliacao(LocalDateTime.now());
        avaliacao.setEspecie(especie);
        avaliacao.setUsuario(usuario);

        Avaliacao salva = avaliacaoRepository.save(avaliacao);
        return converterParaDTO.apply(salva);
    }

    public void deletar(Long id) {
        if (!avaliacaoRepository.existsById(id)) {
            throw new EntityNotFoundException("Avaliação não encontrada");
        }
        avaliacaoRepository.deleteById(id);
    }

    private final java.util.function.Function<Avaliacao, AvaliacaoResponseDTO> converterParaDTO = avaliacao ->
            AvaliacaoResponseDTO.builder()
                    .id(avaliacao.getId())
                    .nota(avaliacao.getNota())
                    .comentario(avaliacao.getComentario())
                    .dataAvaliacao(avaliacao.getDataAvaliacao())
                    .especieId(avaliacao.getEspecie().getId())
                    .nomeEspecie(avaliacao.getEspecie().getNomePopular())
                    .usuarioId(avaliacao.getUsuario().getId())
                    .nomeUsuario(avaliacao.getUsuario().getNome())
                    .build();
}