package br.cefetmg.grow.service;

import br.cefetmg.grow.dto.PlantaUsuarioRequestDTO;
import br.cefetmg.grow.dto.PlantaUsuarioResponseDTO;
import br.cefetmg.grow.exception.ResourceNotFoundException;
import br.cefetmg.grow.model.EspeciePlanta;
import br.cefetmg.grow.model.FasePlanta;
import br.cefetmg.grow.model.PlantaUsuario;
import br.cefetmg.grow.model.Usuario;
import br.cefetmg.grow.repository.EspeciePlantaRepository;
import br.cefetmg.grow.repository.FasePlantaRepository;
import br.cefetmg.grow.repository.PlantaUsuarioRepository;
import br.cefetmg.grow.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PlantaUsuarioService {

    private final PlantaUsuarioRepository plantaRepository;
    private final UsuarioRepository usuarioRepository;
    private final EspeciePlantaRepository especieRepository;
    private final FasePlantaRepository faseRepository;

    @Transactional(readOnly = true)
    public List<PlantaUsuarioResponseDTO> listarTodos() {
        return plantaRepository.findAll().stream()
                .map(PlantaUsuarioResponseDTO::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<PlantaUsuarioResponseDTO> listarPorUsuario(Long usuarioId) {
        return plantaRepository.findByUsuarioId(usuarioId).stream()
                .map(PlantaUsuarioResponseDTO::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public PlantaUsuarioResponseDTO buscarPorId(Long id) {
        PlantaUsuario planta = plantaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Planta do usuário não encontrada. Id: " + id));
        return new PlantaUsuarioResponseDTO(planta);
    }

    @Transactional
    public PlantaUsuarioResponseDTO criar(PlantaUsuarioRequestDTO dto) {
        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado. Id: " + dto.getUsuarioId()));

        EspeciePlanta especie = especieRepository.findById(dto.getEspecieId())
                .orElseThrow(() -> new ResourceNotFoundException("Espécie não encontrada. Id: " + dto.getEspecieId()));

        FasePlanta fase = faseRepository.findById(dto.getFaseId())
                .orElseThrow(() -> new ResourceNotFoundException("Fase não encontrada. Id: " + dto.getFaseId()));

        PlantaUsuario planta = new PlantaUsuario();
        planta.setApelido(dto.getApelido());
        planta.setUsuario(usuario);
        planta.setEspeciePlanta(especie);
        planta.setFase(fase);
        planta.setDataCriacao(LocalDate.now());
        planta.setUltimaAlteracao(LocalDate.now());
        planta.setXpAtual(0);
        planta.setNivel(1);
        planta.setFelicidade(100);
        planta.setSaude(100);

        PlantaUsuario salvo = plantaRepository.save(planta);
        return new PlantaUsuarioResponseDTO(salvo);
    }

    @Transactional
    public PlantaUsuarioResponseDTO atualizar(Long id, PlantaUsuarioRequestDTO dto) {
        PlantaUsuario planta = plantaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Planta do usuário não encontrada. Id: " + id));

        // Verifica se a espécie existe (se foi alterada)
        if (!planta.getEspeciePlanta().getId().equals(dto.getEspecieId())) {
            EspeciePlanta especie = especieRepository.findById(dto.getEspecieId())
                    .orElseThrow(() -> new ResourceNotFoundException("Espécie não encontrada. Id: " + dto.getEspecieId()));
            planta.setEspeciePlanta(especie);
        }

        // Verifica se a fase existe (se foi alterada)
        if (!planta.getFase().getId().equals(dto.getFaseId())) {
            FasePlanta fase = faseRepository.findById(dto.getFaseId())
                    .orElseThrow(() -> new ResourceNotFoundException("Fase não encontrada. Id: " + dto.getFaseId()));
            planta.setFase(fase);
        }

        planta.setApelido(dto.getApelido());
        planta.setUltimaAlteracao(LocalDate.now());

        PlantaUsuario atualizado = plantaRepository.save(planta);
        return new PlantaUsuarioResponseDTO(atualizado);
    }

    @Transactional
    public void excluir(Long id) {
        if (!plantaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Planta do usuário não encontrada. Id: " + id);
        }
        plantaRepository.deleteById(id);
    }

    // Método auxiliar para atualizar felicidade/saúde (pode ser chamado por outros serviços)
    @Transactional
    public PlantaUsuarioResponseDTO atualizarStatus(Long id, Integer felicidade, Integer saude) {
        PlantaUsuario planta = plantaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Planta do usuário não encontrada. Id: " + id));

        if (felicidade != null) planta.setFelicidade(felicidade);
        if (saude != null) planta.setSaude(saude);
        planta.setUltimaAlteracao(LocalDate.now());

        PlantaUsuario atualizado = plantaRepository.save(planta);
        return new PlantaUsuarioResponseDTO(atualizado);
    }
}