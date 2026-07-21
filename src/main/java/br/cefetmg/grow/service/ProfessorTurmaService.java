package br.cefetmg.grow.service;

import br.cefetmg.grow.dto.ProfessorTurmaRequestDTO;
import br.cefetmg.grow.dto.ProfessorTurmaResponseDTO;
import br.cefetmg.grow.exception.ResourceNotFoundException;
import br.cefetmg.grow.model.ProfessorTurma;
import br.cefetmg.grow.model.Turma;
import br.cefetmg.grow.model.Usuario;
import br.cefetmg.grow.repository.ProfessorTurmaRepository;
import br.cefetmg.grow.repository.TurmaRepository;
import br.cefetmg.grow.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProfessorTurmaService {

    private final ProfessorTurmaRepository professorTurmaRepository;
    private final UsuarioRepository usuarioRepository;
    private final TurmaRepository turmaRepository;
    private final ModelMapper modelMapper;

    @Transactional(readOnly = true)
    public List<ProfessorTurmaResponseDTO> listarTodos() {
        return professorTurmaRepository.findAll().stream()
                .map(ProfessorTurmaResponseDTO::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<ProfessorTurmaResponseDTO> listarPorUsuario(Long usuarioId) {
        if (!usuarioRepository.existsById(usuarioId)) {
            throw new ResourceNotFoundException("Usuário não encontrado. Id: " + usuarioId);
        }
        return professorTurmaRepository.findByUsuarioId(usuarioId).stream()
                .map(ProfessorTurmaResponseDTO::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<ProfessorTurmaResponseDTO> listarPorTurma(Long turmaId) {
        if (!turmaRepository.existsById(turmaId)) {
            throw new ResourceNotFoundException("Turma não encontrada. Id: " + turmaId);
        }
        return professorTurmaRepository.findByTurmaId(turmaId).stream()
                .map(ProfessorTurmaResponseDTO::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public ProfessorTurmaResponseDTO buscarPorId(Long id) {
        ProfessorTurma professorTurma = professorTurmaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vínculo professor-turma não encontrado. Id: " + id));
        return new ProfessorTurmaResponseDTO(professorTurma);
    }

    @Transactional
    public ProfessorTurmaResponseDTO criar(ProfessorTurmaRequestDTO dto) {
        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado. Id: " + dto.getUsuarioId()));

        Turma turma = turmaRepository.findById(dto.getTurmaId())
                .orElseThrow(() -> new ResourceNotFoundException("Turma não encontrada. Id: " + dto.getTurmaId()));

        ProfessorTurma professorTurma = modelMapper.map(dto, ProfessorTurma.class);
        professorTurma.setUsuario(usuario);
        professorTurma.setTurma(turma);
        professorTurma.setDataVinculo(LocalDate.now());

        ProfessorTurma salvo = professorTurmaRepository.save(professorTurma);
        return new ProfessorTurmaResponseDTO(salvo);
    }

    @Transactional
    public ProfessorTurmaResponseDTO atualizar(Long id, ProfessorTurmaRequestDTO dto) {
        ProfessorTurma professorTurma = professorTurmaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vínculo professor-turma não encontrado. Id: " + id));

        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado. Id: " + dto.getUsuarioId()));

        Turma turma = turmaRepository.findById(dto.getTurmaId())
                .orElseThrow(() -> new ResourceNotFoundException("Turma não encontrada. Id: " + dto.getTurmaId()));

        modelMapper.map(dto, professorTurma);
        professorTurma.setId(id);
        professorTurma.setUsuario(usuario);
        professorTurma.setTurma(turma);

        ProfessorTurma atualizado = professorTurmaRepository.save(professorTurma);
        return new ProfessorTurmaResponseDTO(atualizado);
    }

    @Transactional
    public void excluir(Long id) {
        if (!professorTurmaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Vínculo professor-turma não encontrado. Id: " + id);
        }
        professorTurmaRepository.deleteById(id);
    }

    @Transactional
    public ProfessorTurmaResponseDTO atualizarPermissoes(Long id, Boolean excluir, Boolean edicao, Boolean gerenciarTarefas) {
        ProfessorTurma professorTurma = professorTurmaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vínculo professor-turma não encontrado. Id: " + id));
        
        if (excluir != null) professorTurma.setExcluir(excluir);
        if (edicao != null) professorTurma.setEdicao(edicao);
        if (gerenciarTarefas != null) professorTurma.setGerenciarTarefas(gerenciarTarefas);
        
        ProfessorTurma atualizado = professorTurmaRepository.save(professorTurma);
        return new ProfessorTurmaResponseDTO(atualizado);
    }
}