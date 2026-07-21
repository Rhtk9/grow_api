package br.cefetmg.grow.service;

import br.cefetmg.grow.dto.RegistroRequestDTO;
import br.cefetmg.grow.dto.RegistroResponseDTO;
import br.cefetmg.grow.exception.ResourceNotFoundException;
import br.cefetmg.grow.model.PlantaUsuario;
import br.cefetmg.grow.model.Registro;
import br.cefetmg.grow.repository.PlantaUsuarioRepository;
import br.cefetmg.grow.repository.RegistroRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RegistroService {

    private final RegistroRepository registroRepository;
    private final PlantaUsuarioRepository plantaUsuarioRepository;
    private final ModelMapper modelMapper;

    @Transactional(readOnly = true)
    public List<RegistroResponseDTO> listarTodos() {
        return registroRepository.findAll().stream()
                .map(RegistroResponseDTO::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<RegistroResponseDTO> listarPorPlanta(Long plantaUsuarioId) {
        if (!plantaUsuarioRepository.existsById(plantaUsuarioId)) {
            throw new ResourceNotFoundException("Planta do usuário não encontrada. Id: " + plantaUsuarioId);
        }
        return registroRepository.findByPlantaUsuarioId(plantaUsuarioId).stream()
                .map(RegistroResponseDTO::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public RegistroResponseDTO buscarPorId(Long id) {
        Registro registro = registroRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Registro não encontrado. Id: " + id));
        return new RegistroResponseDTO(registro);
    }

    @Transactional
    public RegistroResponseDTO criar(RegistroRequestDTO dto) {
        PlantaUsuario plantaUsuario = plantaUsuarioRepository.findById(dto.getPlantaUsuarioId())
                .orElseThrow(() -> new ResourceNotFoundException("Planta do usuário não encontrada. Id: " + dto.getPlantaUsuarioId()));

        Registro registro = modelMapper.map(dto, Registro.class);
        registro.setPlantaUsuario(plantaUsuario);
        registro.setDataRegistro(LocalDate.now());

        Registro salvo = registroRepository.save(registro);
        return new RegistroResponseDTO(salvo);
    }

    @Transactional
    public RegistroResponseDTO atualizar(Long id, RegistroRequestDTO dto) {
        Registro registro = registroRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Registro não encontrado. Id: " + id));

        PlantaUsuario plantaUsuario = plantaUsuarioRepository.findById(dto.getPlantaUsuarioId())
                .orElseThrow(() -> new ResourceNotFoundException("Planta do usuário não encontrada. Id: " + dto.getPlantaUsuarioId()));

        modelMapper.map(dto, registro);
        registro.setId(id);
        registro.setPlantaUsuario(plantaUsuario);

        Registro atualizado = registroRepository.save(registro);
        return new RegistroResponseDTO(atualizado);
    }

    @Transactional
    public void excluir(Long id) {
        if (!registroRepository.existsById(id)) {
            throw new ResourceNotFoundException("Registro não encontrado. Id: " + id);
        }
        registroRepository.deleteById(id);
    }
}