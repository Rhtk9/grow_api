package br.cefetmg.grow.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.cefetmg.grow.dto.EspeciePlantaRequestDTO;
import br.cefetmg.grow.dto.EspeciePlantaResponseDTO;
import br.cefetmg.grow.exception.BusinessException;
import br.cefetmg.grow.exception.ResourceNotFoundException;
import br.cefetmg.grow.model.EspeciePlanta;
import br.cefetmg.grow.model.Usuario;
import br.cefetmg.grow.repository.EspeciePlantaRepository;
import br.cefetmg.grow.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EspeciePlantaService {

    private final EspeciePlantaRepository especiePlantaRepository;
    private final UsuarioRepository usuarioRepository;

    @Transactional(readOnly = true)
    public List<EspeciePlantaResponseDTO> listar() {
        return especiePlantaRepository.findAll().stream()
                .map(EspeciePlantaResponseDTO::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<EspeciePlantaResponseDTO> listarPublicas() {
        return especiePlantaRepository.findByPublicaTrue().stream()
                .map(EspeciePlantaResponseDTO::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public EspeciePlantaResponseDTO buscarPorId(Long id) {
        EspeciePlanta especie = especiePlantaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Espécie não encontrada. Id: " + id));
        return new EspeciePlantaResponseDTO(especie);
    }

    @Transactional(readOnly = true)
    public List<EspeciePlantaResponseDTO> buscarPorNomePopular(String nome) {
        return especiePlantaRepository.findByNomePopularContainingIgnoreCase(nome).stream()
                .map(EspeciePlantaResponseDTO::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<EspeciePlantaResponseDTO> buscarPorNomeCientifico(String nome) {
        return especiePlantaRepository.findByNomeCientificoContainingIgnoreCase(nome).stream()
                .map(EspeciePlantaResponseDTO::new)
                .toList();
    }

    @Transactional
    public EspeciePlantaResponseDTO inserir(EspeciePlantaRequestDTO dto) {
        if (especiePlantaRepository.existsByNomeCientifico(dto.getNomeCientifico())) {
            throw new BusinessException("Já existe uma espécie com este nome científico.");
        }

        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado. Id: " + dto.getUsuarioId()));

        EspeciePlanta especie = new EspeciePlanta();
        especie.setNomePopular(dto.getNomePopular());
        especie.setNomeCientifico(dto.getNomeCientifico());
        especie.setClassificacao(dto.getClassificacao());
        especie.setDescricao(dto.getDescricao());
        especie.setDificuldadeCultivo(dto.getDificuldadeCultivo());
        especie.setAmbiente(dto.getAmbiente());
        especie.setImagem(dto.getImagem());
        especie.setTemperatura(dto.getTemperatura());
        especie.setUmidade(dto.getUmidade());
        especie.setLuminosidade(dto.getLuminosidade());
        especie.setRaridade(dto.getRaridade());
        especie.setPublica(dto.getPublica() != null ? dto.getPublica() : false);
        especie.setUsuario(usuario);

        EspeciePlanta salva = especiePlantaRepository.save(especie);
        return new EspeciePlantaResponseDTO(salva);
    }

    @Transactional
    public EspeciePlantaResponseDTO atualizar(Long id, EspeciePlantaRequestDTO dto) {
        EspeciePlanta especie = especiePlantaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Espécie não encontrada. Id: " + id));

        if (!especie.getNomeCientifico().equalsIgnoreCase(dto.getNomeCientifico()) &&
                especiePlantaRepository.existsByNomeCientifico(dto.getNomeCientifico())) {
            throw new BusinessException("Já existe uma espécie com este nome científico.");
        }

        especie.setNomePopular(dto.getNomePopular());
        especie.setNomeCientifico(dto.getNomeCientifico());
        especie.setClassificacao(dto.getClassificacao());
        especie.setDescricao(dto.getDescricao());
        especie.setDificuldadeCultivo(dto.getDificuldadeCultivo());
        especie.setAmbiente(dto.getAmbiente());
        especie.setTemperatura(dto.getTemperatura());
        especie.setUmidade(dto.getUmidade());
        especie.setLuminosidade(dto.getLuminosidade());
        especie.setRaridade(dto.getRaridade());
        especie.setPublica(dto.getPublica() != null ? dto.getPublica() : false);

        if (dto.getImagem() != null) {
            especie.setImagem(dto.getImagem());
        }

        if (dto.getUsuarioId() != null && !especie.getUsuario().getId().equals(dto.getUsuarioId())) {
            Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                    .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado. Id: " + dto.getUsuarioId()));
            especie.setUsuario(usuario);
        }

        EspeciePlanta atualizada = especiePlantaRepository.save(especie);
        return new EspeciePlantaResponseDTO(atualizada);
    }

    @Transactional
    public void excluir(Long id) {
        if (!especiePlantaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Espécie não encontrada. Id: " + id);
        }
        especiePlantaRepository.deleteById(id);
    }
}