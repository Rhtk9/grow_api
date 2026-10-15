package br.cefetmg.grow.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.cefetmg.grow.dto.EvolucaoFaseRequestDTO;
import br.cefetmg.grow.dto.EvolucaoFaseResponseDTO;
import br.cefetmg.grow.exception.ResourceNotFoundException;
import br.cefetmg.grow.model.EvolucaoFase;
import br.cefetmg.grow.repository.EvolucaoFaseRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EvolucaoFaseService {

    private final EvolucaoFaseRepository evolucaoFaseRepository;

    @Transactional(readOnly = true)
    public List<EvolucaoFaseResponseDTO> listarTodos() {
        return evolucaoFaseRepository.findAll().stream()
                .map(EvolucaoFaseResponseDTO::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public EvolucaoFaseResponseDTO buscarPorId(Long id) {
        EvolucaoFase evolucao = evolucaoFaseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Evolução de fase não encontrada. Id: " + id));
        return new EvolucaoFaseResponseDTO(evolucao);
    }

    @Transactional
    public EvolucaoFaseResponseDTO criar(EvolucaoFaseRequestDTO dto) {
        EvolucaoFase evolucao = new EvolucaoFase();
        evolucao.setBonus(dto.getBonus() != null ? dto.getBonus() : 0);
        evolucao.setPenalidade(dto.getPenalidade() != null ? dto.getPenalidade() : 0);
        evolucao.setDiasRuim(dto.getDiasRuim());
        evolucao.setDiasMedios(dto.getDiasMedios());
        evolucao.setDiasBons(dto.getDiasBons());

        EvolucaoFase salva = evolucaoFaseRepository.save(evolucao);
        return new EvolucaoFaseResponseDTO(salva);
    }

    @Transactional
    public EvolucaoFaseResponseDTO atualizar(Long id, EvolucaoFaseRequestDTO dto) {
        EvolucaoFase evolucao = evolucaoFaseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Evolução de fase não encontrada. Id: " + id));

        evolucao.setBonus(dto.getBonus() != null ? dto.getBonus() : 0);
        evolucao.setPenalidade(dto.getPenalidade() != null ? dto.getPenalidade() : 0);
        evolucao.setDiasRuim(dto.getDiasRuim());
        evolucao.setDiasMedios(dto.getDiasMedios());
        evolucao.setDiasBons(dto.getDiasBons());

        EvolucaoFase atualizada = evolucaoFaseRepository.save(evolucao);
        return new EvolucaoFaseResponseDTO(atualizada);
    }

    @Transactional
    public void excluir(Long id) {
        if (!evolucaoFaseRepository.existsById(id)) {
            throw new ResourceNotFoundException("Evolução de fase não encontrada. Id: " + id);
        }
        evolucaoFaseRepository.deleteById(id);
    }
}