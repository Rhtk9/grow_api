package br.cefetmg.grow.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.cefetmg.grow.dto.FasePlantaRequestDTO;
import br.cefetmg.grow.dto.FasePlantaResponseDTO;
import br.cefetmg.grow.exception.BusinessException;
import br.cefetmg.grow.exception.ResourceNotFoundException;
import br.cefetmg.grow.model.EspeciePlanta;
import br.cefetmg.grow.model.EvolucaoFase;
import br.cefetmg.grow.model.FasePlanta;
import br.cefetmg.grow.model.NecessidadeFase;
import br.cefetmg.grow.repository.EspeciePlantaRepository;
import br.cefetmg.grow.repository.EvolucaoFaseRepository;
import br.cefetmg.grow.repository.FasePlantaRepository;
import br.cefetmg.grow.repository.NecessidadeFaseRepository;
import lombok.RequiredArgsConstructor;

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
        return fasePlantaRepository.findByEspeciePlantaId(especieId).stream()
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

        if (fasePlantaRepository.existsByNomeAndEspeciePlantaId(dto.getNome(), dto.getEspecieId())) {
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
        necessidade.setLuzIdeal(dto.getLuzIdeal());
        necessidade.setRegasPorSemana(dto.getRegasPorSemana());
        necessidade.setAdubacoesPorMes(dto.getAdubacoesPorMes());
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
                fasePlantaRepository.existsByNomeAndEspeciePlantaIdAndIdNot(dto.getNome(), dto.getEspecieId(), id)) {
            throw new BusinessException("Já existe uma fase com esse nome para esta espécie.");
        }

        fase.setNome(dto.getNome());
        fase.setOrdem(dto.getOrdem());
        fase.setDiasBase(dto.getDiasBase());
        fase.setXpNecessario(dto.getXpNecessario());
        fase.setXpGanho(dto.getXpGanho());
        fase.setImagem(dto.getImagem());
        fase.setEspeciePlanta(especie);

        EvolucaoFase evolucao = fase.getEvolucaoFase();
        if (evolucao == null) {
            evolucao = new EvolucaoFase();
        }
        evolucao.setDiasRuim(dto.getDiasRuim());
        evolucao.setDiasMedios(dto.getDiasMedios());
        evolucao.setDiasBons(dto.getDiasBons());
        evolucao.setBonus(dto.getBonus() != null ? dto.getBonus() : 0);
        evolucao.setPenalidade(dto.getPenalidade() != null ? dto.getPenalidade() : 0);
        fase.setEvolucaoFase(evolucaoFaseRepository.save(evolucao));

        NecessidadeFase necessidade = fase.getNecessidadeFase();
        if (necessidade == null) {
            necessidade = new NecessidadeFase();
        }
        necessidade.setAguaMedia(dto.getAguaMedia());
        necessidade.setLuzMedia(dto.getLuzMedia());
        necessidade.setTemperaturaMedia(dto.getTemperaturaMedia());
        necessidade.setUmidadeMedia(dto.getUmidadeMedia());
        fase.setNecessidadeFase(necessidadeFaseRepository.save(necessidade));

        FasePlanta atualizada = fasePlantaRepository.save(fase);
        return new FasePlantaResponseDTO(atualizada);
    }

    @Transactional
    public void excluir(Long id) {
        FasePlanta fase = fasePlantaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Fase de planta não encontrada. Id: " + id));

        Long evolucaoId = (fase.getEvolucaoFase() != null) ? fase.getEvolucaoFase().getId() : null;
        Long necessidadeId = (fase.getNecessidadeFase() != null) ? fase.getNecessidadeFase().getId() : null;

        fasePlantaRepository.deleteById(id);

        if (evolucaoId != null) {
            evolucaoFaseRepository.deleteById(evolucaoId);
        }
        if (necessidadeId != null) {
            necessidadeFaseRepository.deleteById(necessidadeId);
        }
    }
}