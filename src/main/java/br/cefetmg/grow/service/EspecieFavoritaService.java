package br.cefetmg.grow.service;

import br.cefetmg.grow.dto.EspecieFavoritaRequestDTO;
import br.cefetmg.grow.dto.EspecieFavoritaResponseDTO;
import br.cefetmg.grow.exception.ResourceNotFoundException;
import br.cefetmg.grow.exception.BusinessException;
import br.cefetmg.grow.model.EspecieFavorita;
import br.cefetmg.grow.model.EspeciePlanta;
import br.cefetmg.grow.model.Usuario;
import br.cefetmg.grow.repository.EspecieFavoritaRepository;
import br.cefetmg.grow.repository.EspeciePlantaRepository;
import br.cefetmg.grow.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EspecieFavoritaService {

    private final EspecieFavoritaRepository favoritoRepository;
    private final UsuarioRepository usuarioRepository;
    private final EspeciePlantaRepository especieRepository;

    @Transactional(readOnly = true)
    public List<EspecieFavoritaResponseDTO> listarTodos() {
        return favoritoRepository.findAll().stream()
                .map(EspecieFavoritaResponseDTO::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<EspecieFavoritaResponseDTO> listarPorUsuario(Long usuarioId) {
        return favoritoRepository.findByUsuarioId(usuarioId).stream()
                .map(EspecieFavoritaResponseDTO::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public EspecieFavoritaResponseDTO buscarPorId(Long id) {
        EspecieFavorita favorito = favoritoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Favorito não encontrado. Id: " + id));
        return new EspecieFavoritaResponseDTO(favorito);
    }

    @Transactional
    public EspecieFavoritaResponseDTO favoritar(EspecieFavoritaRequestDTO dto) {
        // Verifica se usuário existe
        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado. Id: " + dto.getUsuarioId()));

        // Verifica se espécie existe
        EspeciePlanta especie = especieRepository.findById(dto.getEspecieId())
                .orElseThrow(() -> new ResourceNotFoundException("Espécie não encontrada. Id: " + dto.getEspecieId()));

        // Verifica se já foi favoritada
        if (favoritoRepository.existsByUsuarioIdAndEspeciePlantaId(dto.getUsuarioId(), dto.getEspecieId())) {
            throw new BusinessException("Esta espécie já foi favoritada pelo usuário.");
        }

        EspecieFavorita favorito = new EspecieFavorita();
        favorito.setUsuario(usuario);
        favorito.setEspeciePlanta(especie);
        favorito.setDataFavorito(LocalDate.now());

        EspecieFavorita salvo = favoritoRepository.save(favorito);
        return new EspecieFavoritaResponseDTO(salvo);
    }

    @Transactional
    public void desfavoritar(Long usuarioId, Long especieId) {
        EspecieFavorita favorito = favoritoRepository.findByUsuarioIdAndEspeciePlantaId(usuarioId, especieId)
                .orElseThrow(() -> new ResourceNotFoundException("Favorito não encontrado para este usuário e espécie."));

        favoritoRepository.delete(favorito);
    }

    @Transactional
    public void excluir(Long id) {
        if (!favoritoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Favorito não encontrado. Id: " + id);
        }
        favoritoRepository.deleteById(id);
    }
}