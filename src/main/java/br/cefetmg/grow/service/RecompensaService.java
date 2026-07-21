package br.cefetmg.grow.service;

import br.cefetmg.grow.dto.RecompensaRequestDTO;
import br.cefetmg.grow.dto.RecompensaResponseDTO;
import br.cefetmg.grow.exception.ResourceNotFoundException;
import br.cefetmg.grow.model.Recompensa;
import br.cefetmg.grow.repository.RecompensaRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecompensaService {

    private final RecompensaRepository recompensaRepository;
    private final ModelMapper modelMapper;

    @Transactional(readOnly = true)
    public List<RecompensaResponseDTO> listarTodas() {
        return recompensaRepository.findAll().stream()
                .map(RecompensaResponseDTO::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<RecompensaResponseDTO> listarPorRaridade(String raridade) {
        return recompensaRepository.findByRaridade(raridade).stream()
                .map(RecompensaResponseDTO::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<RecompensaResponseDTO> listarPorTipo(String tipo) {
        return recompensaRepository.findByTipo(tipo).stream()
                .map(RecompensaResponseDTO::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<RecompensaResponseDTO> listarDisponiveis(Integer xpUsuario) {
        return recompensaRepository.findByXpNecessarioLessThanEqual(xpUsuario).stream()
                .map(RecompensaResponseDTO::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public RecompensaResponseDTO buscarPorId(Long id) {
        Recompensa recompensa = recompensaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Recompensa não encontrada. Id: " + id));
        return new RecompensaResponseDTO(recompensa);
    }

    @Transactional
    public RecompensaResponseDTO criar(RecompensaRequestDTO dto) {
        Recompensa recompensa = modelMapper.map(dto, Recompensa.class);
        Recompensa salva = recompensaRepository.save(recompensa);
        return new RecompensaResponseDTO(salva);
    }

    @Transactional
    public RecompensaResponseDTO atualizar(Long id, RecompensaRequestDTO dto) {
        Recompensa recompensa = recompensaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Recompensa não encontrada. Id: " + id));

        modelMapper.map(dto, recompensa);
        recompensa.setId(id);

        Recompensa atualizada = recompensaRepository.save(recompensa);
        return new RecompensaResponseDTO(atualizada);
    }

    @Transactional
    public void excluir(Long id) {
        if (!recompensaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Recompensa não encontrada. Id: " + id);
        }
        recompensaRepository.deleteById(id);
    }
}
