package br.cefetmg.grow.service;

import br.cefetmg.grow.dto.UsuarioRecompensaRequestDTO;
import br.cefetmg.grow.dto.UsuarioRecompensaResponseDTO;
import br.cefetmg.grow.exception.ResourceNotFoundException;
import br.cefetmg.grow.model.Recompensa;
import br.cefetmg.grow.model.Usuario;
import br.cefetmg.grow.model.UsuarioRecompensa;
import br.cefetmg.grow.repository.RecompensaRepository;
import br.cefetmg.grow.repository.UsuarioRecompensaRepository;
import br.cefetmg.grow.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioRecompensaService {

    private final UsuarioRecompensaRepository usuarioRecompensaRepository;
    private final UsuarioRepository usuarioRepository;
    private final RecompensaRepository recompensaRepository;
    private final ModelMapper modelMapper;

    @Transactional(readOnly = true)
    public List<UsuarioRecompensaResponseDTO> listarTodos() {
        return usuarioRecompensaRepository.findAll().stream()
                .map(UsuarioRecompensaResponseDTO::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<UsuarioRecompensaResponseDTO> listarPorUsuario(Long usuarioId) {
        if (!usuarioRepository.existsById(usuarioId)) {
            throw new ResourceNotFoundException("Usuário não encontrado. Id: " + usuarioId);
        }
        return usuarioRecompensaRepository.findByUsuarioId(usuarioId).stream()
                .map(UsuarioRecompensaResponseDTO::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<UsuarioRecompensaResponseDTO> listarEquipadasPorUsuario(Long usuarioId) {
        return usuarioRecompensaRepository.findRecompensasEquipadasPorUsuario(usuarioId).stream()
                .map(UsuarioRecompensaResponseDTO::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public UsuarioRecompensaResponseDTO buscarPorId(Long id) {
        UsuarioRecompensa usuarioRecompensa = usuarioRecompensaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Desbloqueio de recompensa não encontrado. Id: " + id));
        return new UsuarioRecompensaResponseDTO(usuarioRecompensa);
    }

    @Transactional
    public UsuarioRecompensaResponseDTO desbloquear(UsuarioRecompensaRequestDTO dto) {
        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado. Id: " + dto.getUsuarioId()));

        Recompensa recompensa = recompensaRepository.findById(dto.getRecompensaId())
                .orElseThrow(() -> new ResourceNotFoundException("Recompensa não encontrada. Id: " + dto.getRecompensaId()));

        // Verifica se o usuário já desbloqueou esta recompensa
        List<UsuarioRecompensa> existentes = usuarioRecompensaRepository.findByUsuarioId(dto.getUsuarioId());
        boolean jaDesbloqueada = existentes.stream()
                .anyMatch(ur -> ur.getRecompensa().getId().equals(dto.getRecompensaId()));
        
        if (jaDesbloqueada) {
            throw new IllegalArgumentException("Usuário já desbloqueou esta recompensa");
        }

        // Verifica se o usuário tem XP suficiente
        if (usuario.getXpTotal() < recompensa.getXpNecessario()) {
            throw new IllegalArgumentException("Usuário não tem XP suficiente para desbloquear esta recompensa");
        }

        UsuarioRecompensa usuarioRecompensa = modelMapper.map(dto, UsuarioRecompensa.class);
        usuarioRecompensa.setUsuario(usuario);
        usuarioRecompensa.setRecompensa(recompensa);
        usuarioRecompensa.setDataDesbloqueio(LocalDate.now());

        // Se for a primeira recompensa equipada, equipa automaticamente
        long totalEquipadas = usuarioRecompensaRepository.findRecompensasEquipadasPorUsuario(dto.getUsuarioId()).size();
        if (totalEquipadas == 0) {
            usuarioRecompensa.setEquipada(true);
        }

        UsuarioRecompensa salvo = usuarioRecompensaRepository.save(usuarioRecompensa);
        return new UsuarioRecompensaResponseDTO(salvo);
    }

    @Transactional
    public void excluir(Long id) {
        if (!usuarioRecompensaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Desbloqueio de recompensa não encontrado. Id: " + id);
        }
        usuarioRecompensaRepository.deleteById(id);
    }

    @Transactional
    public UsuarioRecompensaResponseDTO equipar(Long id) {
        UsuarioRecompensa usuarioRecompensa = usuarioRecompensaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Desbloqueio de recompensa não encontrado. Id: " + id));
        
        List<UsuarioRecompensa> outras = usuarioRecompensaRepository.findRecompensasEquipadasPorUsuario(
                usuarioRecompensa.getUsuario().getId());
        outras.forEach(ur -> {
            ur.setEquipada(false);
            usuarioRecompensaRepository.save(ur);
        });
        
        usuarioRecompensa.setEquipada(true);
        UsuarioRecompensa atualizado = usuarioRecompensaRepository.save(usuarioRecompensa);
        return new UsuarioRecompensaResponseDTO(atualizado);
    }

    @Transactional
    public UsuarioRecompensaResponseDTO desequipar(Long id) {
        UsuarioRecompensa usuarioRecompensa = usuarioRecompensaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Desbloqueio de recompensa não encontrado. Id: " + id));
        
        usuarioRecompensa.setEquipada(false);
        UsuarioRecompensa atualizado = usuarioRecompensaRepository.save(usuarioRecompensa);
        return new UsuarioRecompensaResponseDTO(atualizado);
    }
}