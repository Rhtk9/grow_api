package br.cefetmg.grow.service;

import br.cefetmg.grow.dto.TurmaRequestDTO;
import br.cefetmg.grow.dto.TurmaResponseDTO;
import br.cefetmg.grow.exception.ResourceNotFoundException;
import br.cefetmg.grow.model.Disciplina;
import br.cefetmg.grow.model.Turma;
import br.cefetmg.grow.repository.DisciplinaRepository;
import br.cefetmg.grow.repository.TurmaRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TurmaService {

    private final TurmaRepository turmaRepository;
    private final DisciplinaRepository disciplinaRepository;
    private final ModelMapper modelMapper;

    @Transactional(readOnly = true)
    public List<TurmaResponseDTO> listarTodas() {
        return turmaRepository.findAll().stream()
                .map(TurmaResponseDTO::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<TurmaResponseDTO> listarPorDisciplina(Long disciplinaId) {
        if (!disciplinaRepository.existsById(disciplinaId)) {
            throw new ResourceNotFoundException("Disciplina não encontrada. Id: " + disciplinaId);
        }
        return turmaRepository.findByDisciplinaId(disciplinaId).stream()
                .map(TurmaResponseDTO::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<TurmaResponseDTO> listarPorUsuario(Long usuarioId) {
        return turmaRepository.findTurmasByUsuarioId(usuarioId).stream()
                .map(TurmaResponseDTO::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public TurmaResponseDTO buscarPorId(Long id) {
        Turma turma = turmaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Turma não encontrada. Id: " + id));
        return new TurmaResponseDTO(turma);
    }

    @Transactional
    public TurmaResponseDTO criar(TurmaRequestDTO dto) {
        if (turmaRepository.existsByCodigo(dto.getCodigo())) {
            throw new IllegalArgumentException("Já existe uma turma com este código");
        }

        Disciplina disciplina = disciplinaRepository.findById(dto.getDisciplinaId())
                .orElseThrow(() -> new ResourceNotFoundException("Disciplina não encontrada. Id: " + dto.getDisciplinaId()));

        Turma turma = modelMapper.map(dto, Turma.class);
        turma.setDisciplina(disciplina);
        turma.setDataCriacao(LocalDate.now());

        Turma salva = turmaRepository.save(turma);
        return new TurmaResponseDTO(salva);
    }

    @Transactional
    public TurmaResponseDTO atualizar(Long id, TurmaRequestDTO dto) {
        Turma turma = turmaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Turma não encontrada. Id: " + id));

        if (!turma.getCodigo().equals(dto.getCodigo()) && turmaRepository.existsByCodigo(dto.getCodigo())) {
            throw new IllegalArgumentException("Já existe uma turma com este código");
        }

        Disciplina disciplina = disciplinaRepository.findById(dto.getDisciplinaId())
                .orElseThrow(() -> new ResourceNotFoundException("Disciplina não encontrada. Id: " + dto.getDisciplinaId()));

        modelMapper.map(dto, turma);
        turma.setId(id);
        turma.setDisciplina(disciplina);

        Turma atualizada = turmaRepository.save(turma);
        return new TurmaResponseDTO(atualizada);
    }

    @Transactional
    public void excluir(Long id) {
        if (!turmaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Turma não encontrada. Id: " + id);
        }
        turmaRepository.deleteById(id);
    }
}