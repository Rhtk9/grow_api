package br.cefetmg.grow.service;

import br.cefetmg.grow.dto.DisciplinaRequestDTO;
import br.cefetmg.grow.dto.DisciplinaResponseDTO;
import br.cefetmg.grow.exception.ResourceNotFoundException;
import br.cefetmg.grow.model.Disciplina;
import br.cefetmg.grow.repository.DisciplinaRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DisciplinaService {

    private final DisciplinaRepository disciplinaRepository;
    private final ModelMapper modelMapper;

    @Transactional(readOnly = true)
    public List<DisciplinaResponseDTO> listarTodas() {
        return disciplinaRepository.findAll().stream()
                .map(DisciplinaResponseDTO::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<DisciplinaResponseDTO> listarAtivas() {
        return disciplinaRepository.findByAtivaTrue().stream()
                .map(DisciplinaResponseDTO::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public DisciplinaResponseDTO buscarPorId(Long id) {
        Disciplina disciplina = disciplinaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Disciplina não encontrada. Id: " + id));
        return new DisciplinaResponseDTO(disciplina);
    }

    @Transactional
    public DisciplinaResponseDTO criar(DisciplinaRequestDTO dto) {
        if (disciplinaRepository.existsByNome(dto.getNome())) {
            throw new IllegalArgumentException("Já existe uma disciplina com este nome");
        }

        Disciplina disciplina = modelMapper.map(dto, Disciplina.class);
        if (dto.getAtiva() == null) {
            disciplina.setAtiva(true);
        }

        Disciplina salva = disciplinaRepository.save(disciplina);
        return new DisciplinaResponseDTO(salva);
    }

    @Transactional
    public DisciplinaResponseDTO atualizar(Long id, DisciplinaRequestDTO dto) {
        Disciplina disciplina = disciplinaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Disciplina não encontrada. Id: " + id));
      
        if (!disciplina.getNome().equals(dto.getNome()) && disciplinaRepository.existsByNome(dto.getNome())) {
            throw new IllegalArgumentException("Já existe uma disciplina com este nome");
        }

        modelMapper.map(dto, disciplina);
        disciplina.setId(id);

        Disciplina atualizada = disciplinaRepository.save(disciplina);
        return new DisciplinaResponseDTO(atualizada);
    }

    @Transactional
    public void excluir(Long id) {
        if (!disciplinaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Disciplina não encontrada. Id: " + id);
        }
        disciplinaRepository.deleteById(id);
    }

    @Transactional
    public DisciplinaResponseDTO ativarDesativar(Long id, Boolean ativo) {
        Disciplina disciplina = disciplinaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Disciplina não encontrada. Id: " + id));
        
        disciplina.setAtiva(ativo);
        Disciplina atualizada = disciplinaRepository.save(disciplina);
        return new DisciplinaResponseDTO(atualizada);
    }
}