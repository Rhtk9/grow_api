package br.cefetmg.grow.service;

import br.cefetmg.grow.dto.FasePlantaRequestDTO;
import br.cefetmg.grow.dto.FasePlantaResponseDTO;
import br.cefetmg.grow.exception.BusinessException;
import br.cefetmg.grow.exception.ResourceNotFoundException;
import br.cefetmg.grow.model.*;
import br.cefetmg.grow.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FasePlantaService {

    private final FasePlantaRepository fasePlantaRepository;
    private final EspeciePlantaRepository especiePlantaRepository;
    private final EvolucaoFaseRepository evolucaoFaseRepository;
    private final NecessidadeFaseRepository necessidadeFaseRepository;

    @Transactional(readOnly = true)
    public List<FasePlantaResponseDTO> listarTodas() {
        return fasePlantaRepository.findAll().stream()
                .map(FasePlantaResponseDTO::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<FasePlantaResponseDTO> listarPorEspecie(Long especieId) {
        return fasePlantaRepository.findByEspecieId(especieId).stream()
                .map(FasePlantaResponseDTO::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public FasePlantaResponseDTO buscarPorId(Long id) {
        FasePlanta fase = fasePlantaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Fase de planta não encontrada. Id: " + id));
        return new FasePlantaResponseDTO(fase);
    }

    @Transactional
    public FasePlantaResponseDTO inserir(FasePlantaRequestDTO dto) {
        EspeciePlanta especie = especiePlantaRepository.findById(dto.getEspecieId())
                .orElseThrow(() -> new ResourceNotFoundException("Espécie não encontrada. Id: " + dto.getEspecieId()));

        if (fasePlantaRepository.existsByNomeAndEspecieId(dto.getNome(), dto.getEspecieId())) {
            throw new BusinessException("Já existe uma fase com esse nome para esta espécie.");
        }

        EvolucaoFase evolucao = new EvolucaoFase();
        evolucao.setDiasRuim(dto.getDiasRuim());
        evolucao.setDiasMedios(dto.getDiasMedios());
        evolucao.setDiasBons(dto.getDiasBons());
        evolucao.setBonus(dto.getBonus() != null ? dto.getBonus() : 0);
        evolucao.setPenalidade(dto.getPenalidade() != null ? dto.getPenalidade() : 0);
        evolucao = evolucaoFaseRepository.save(evolucao);

        NecessidadeFase necessidade = new NecessidadeFase();
        necessidade.setAguaMedia(dto.getAguaMedia());
        necessidade.setLuzMedia(dto.getLuzMedia());
        necessidade.setTemperaturaMedia(dto.getTemperaturaMedia());
        necessidade.setUmidadeMedia(dto.getUmidadeMedia());
        necessidade = necessidadeFaseRepository.save(necessidade);

        FasePlanta fase = new FasePlanta();
        fase.setNome(dto.getNome());
        fase.setOrdem(dto.getOrdem());
        fase.setDiasBase(dto.getDiasBase());
        fase.setXpNecessario(dto.getXpNecessario());
        fase.setXpGanho(dto.getXpGanho());

        // AJUSTADO: usando setImagem em vez de setImg
        fase.setImagem(dto.getImagem());
        fase.setEspeciePlanta(especie);
        fase.setEvolucaoFase(evolucao);
        fase.setNecessidadeFase(necessidade);

        FasePlanta salva = fasePlantaRepository.save(fase);
        return new FasePlantaResponseDTO(salva);
    }

    @Transactional
    public FasePlantaResponseDTO atualizar(Long id, FasePlantaRequestDTO dto) {
        FasePlanta fase = fasePlantaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Fase de planta não encontrada. Id: " + id));

        EspeciePlanta especie = especiePlantaRepository.findById(dto.getEspecieId())
                .orElseThrow(() -> new ResourceNotFoundException("Espécie não encontrada. Id: " + dto.getEspecieId()));

        if (!fase.getNome().equals(dto.getNome()) &&
                fasePlantaRepository.existsByNomeAndEspecieIdAndIdNot(dto.getNome(), dto.getEspecieId(), id)) {
            throw new BusinessException("Já existe uma fase com esse nome para esta espécie.");
        }

        fase.setNome(dto.getNome());
        fase.setOrdem(dto.getOrdem());
        fase.setDiasBase(dto.getDiasBase());
        fase.setXpNecessario(dto.getXpNecessario());
        fase.setXpGanho(dto.getXpGanho());

        // AJUSTADO: usando setImagem em vez de setImg
        fase.setImagem(dto.getImagem());
        fase.setEspeciePlanta(especie);

        EvolucaoFase evolucao = fase.getEvolucaoFase();
        evolucao.setDiasRuim(dto.getDiasRuim());
        evolucao.setDiasMedios(dto.getDiasMedios());
        evolucao.setDiasBons(dto.getDiasBons());
        evolucao.setBonus(dto.getBonus() != null ? dto.getBonus() : 0);
        evolucao.setPenalidade(dto.getPenalidade() != null ? dto.getPenalidade() : 0);
        evolucaoFaseRepository.save(evolucao);

        NecessidadeFase necessidade = fase.getNecessidadeFase();
        necessidade.setAguaMedia(dto.getAguaMedia());
        necessidade.setLuzMedia(dto.getLuzMedia());
        necessidade.setTemperaturaMedia(dto.getTemperaturaMedia());
        necessidade.setUmidadeMedia(dto.getUmidadeMedia());
        necessidadeFaseRepository.save(necessidade);

        FasePlanta atualizada = fasePlantaRepository.save(fase);
        return new FasePlantaResponseDTO(atualizada);
    }

    @Transactional
    public void excluir(Long id) {
        FasePlanta fase = fasePlantaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Fase de planta não encontrada. Id: " + id));

        // Captura os IDs verificando se os objetos associados não são nulos
        Long evolucaoId = (fase.getEvolucaoFase() != null) ? fase.getEvolucaoFase().getId() : null;
        Long necessidadeId = (fase.getNecessidadeFase() != null) ? fase.getNecessidadeFase().getId() : null;

        // Deleta a fase primeiro para desvincular as chaves estrangeiras
        fasePlantaRepository.deleteById(id);

        // Deleta as entidades dependentes apenas se os IDs existirem
        if (evolucaoId != null) {
            evolucaoFaseRepository.deleteById(evolucaoId);
        }
        if (necessidadeId != null) {
            necessidadeFaseRepository.deleteById(necessidadeId);
        }
    }
}