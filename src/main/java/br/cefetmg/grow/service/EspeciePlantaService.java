package br.cefetmg.grow.service;

import br.cefetmg.grow.dto.EspeciePlantaRequestDTO;
import br.cefetmg.grow.dto.EspeciePlantaResponseDTO;
import br.cefetmg.grow.exception.ResourceNotFoundException;
import br.cefetmg.grow.exception.BusinessException;
import br.cefetmg.grow.model.EspeciePlanta;
import br.cefetmg.grow.model.Usuario;
import br.cefetmg.grow.repository.EspeciePlantaRepository;
import br.cefetmg.grow.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    @Transactional(readOnly = true)
    public List<EspeciePlantaResponseDTO> listarPublicas() {
        return especiePlantaRepository.findByPublicaTrue().stream()
                .map(EspeciePlantaResponseDTO::new)
                .toList();
    }

    @Transactional
    public EspeciePlantaResponseDTO inserir(EspeciePlantaRequestDTO dto) {
        // Validar se o nome científico já existe
        if (especiePlantaRepository.existsByNomeCientifico(dto.getNomeCientifico())) {
            throw new BusinessException("Já existe uma espécie com este nome científico.");
        }

        // Buscar o usuário criador
        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado. Id: " + dto.getUsuarioId()));

        // Criar a entidade
        EspeciePlanta especie = new EspeciePlanta();
        especie.setNomePopular(dto.getNomePopular());
        especie.setNomeCientifico(dto.getNomeCientifico());
        especie.setClassificacao(dto.getClassificacao());
        especie.setDescricao(dto.getDescricao());
        especie.setDificuldadeCultivo(dto.getDificuldadeCultivo());
        especie.setAmbiente(dto.getAmbiente());
        especie.setTemperatura(dto.getTemperatura());
        especie.setUmidade(dto.getUmidade());
        especie.setLuminosidade(dto.getLuminosidade());
        especie.setImagem(dto.getImagem());
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

        // Verificar se o nome científico já existe (excluindo a própria espécie)
        if (!especie.getNomeCientifico().equals(dto.getNomeCientifico()) &&
                especiePlantaRepository.existsByNomeCientifico(dto.getNomeCientifico())) {
            throw new BusinessException("Já existe uma espécie com este nome científico.");
        }

        // Atualizar campos
        especie.setNomePopular(dto.getNomePopular());
        especie.setNomeCientifico(dto.getNomeCientifico());
        especie.setClassificacao(dto.getClassificacao());
        especie.setDescricao(dto.getDescricao());
        especie.setDificuldadeCultivo(dto.getDificuldadeCultivo());
        especie.setAmbiente(dto.getAmbiente());
        especie.setTemperatura(dto.getTemperatura());
        especie.setUmidade(dto.getUmidade());
        especie.setLuminosidade(dto.getLuminosidade());
        especie.setImagem(dto.getImagem());
        especie.setRaridade(dto.getRaridade());
        especie.setPublica(dto.getPublica() != null ? dto.getPublica() : false);

        // Se o usuário for alterado, buscar o novo
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