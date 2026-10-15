package br.cefetmg.grow.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.cefetmg.grow.dto.TurmaRequestDTO;
import br.cefetmg.grow.dto.TurmaResponseDTO;
import br.cefetmg.grow.exception.BusinessException;
import br.cefetmg.grow.exception.ResourceNotFoundException;
import br.cefetmg.grow.model.Disciplina;
import br.cefetmg.grow.model.ProfessorTurma;
import br.cefetmg.grow.model.Turma;
import br.cefetmg.grow.model.Usuario;
import br.cefetmg.grow.repository.DisciplinaRepository;
import br.cefetmg.grow.repository.ProfessorTurmaRepository;
import br.cefetmg.grow.repository.TurmaRepository;
import br.cefetmg.grow.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TurmaService {

    private final TurmaRepository turmaRepository;
    private final DisciplinaRepository disciplinaRepository;
    private final UsuarioRepository usuarioRepository;
    private final ProfessorTurmaRepository professorTurmaRepository;
    private final ModelMapper modelMapper;

    private static final String CARACTERES = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int CODIGO_TAMANHO = 6;
    private static final Random RANDOM = new Random();

    private String gerarCodigoUnico() {
        String codigo;
        do {
            StringBuilder sb = new StringBuilder(CODIGO_TAMANHO);
            for (int i = 0; i < CODIGO_TAMANHO; i++) {
                sb.append(CARACTERES.charAt(RANDOM.nextInt(CARACTERES.length())));
            }
            codigo = sb.toString();
        } while (turmaRepository.existsByCodigo(codigo));
        return codigo;
    }

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
    Long usuarioId = dto.getUsuarioId();
    if (usuarioId == null) {
        throw new IllegalArgumentException("ID do usuário criador é obrigatório");
    }

    Usuario usuario = usuarioRepository.findById(usuarioId)
            .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado. Id: " + usuarioId));

    // 👇 disciplina opcional: só busca se veio um ID válido (> 0)
    Disciplina disciplina = null;
    Long disciplinaId = dto.getDisciplinaId();
    if (disciplinaId != null && disciplinaId > 0) {
        disciplina = disciplinaRepository.findById(disciplinaId)
                .orElseThrow(() -> new ResourceNotFoundException("Disciplina não encontrada. Id: " + disciplinaId));
    }

    String codigo = gerarCodigoUnico();

    Turma turma = new Turma();
    turma.setNome(dto.getNome());
    turma.setCodigo(codigo);
    turma.setDescricao(dto.getDescricao());
    turma.setAno(dto.getAno());
    turma.setNivelEnsino(dto.getNivelEnsino());
    turma.setDisciplina(disciplina); // 👈 pode ser null agora
    turma.setDataCriacao(LocalDate.now());

    Turma salva = turmaRepository.save(turma);

    // Vincula o criador como professor com todas as permissões
    ProfessorTurma professorTurma = new ProfessorTurma();
    professorTurma.setUsuario(usuario);
    professorTurma.setTurma(salva);
    professorTurma.setDataVinculo(LocalDate.now());
    professorTurma.setExcluir(true);
    professorTurma.setEdicao(true);
    professorTurma.setGerenciarTarefas(true);
    professorTurmaRepository.save(professorTurma);

    return new TurmaResponseDTO(salva);
}

    @Transactional
public TurmaResponseDTO atualizar(Long id, TurmaRequestDTO dto) {
    Turma turma = turmaRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Turma não encontrada. Id: " + id));

    if (!turma.getCodigo().equals(dto.getCodigo()) && turmaRepository.existsByCodigo(dto.getCodigo())) {
        throw new IllegalArgumentException("Já existe uma turma com este código");
    }

    turma.setNome(dto.getNome());
    turma.setDescricao(dto.getDescricao());
    turma.setAno(dto.getAno());
    turma.setNivelEnsino(dto.getNivelEnsino());

   
    Long disciplinaId = dto.getDisciplinaId();
    if (disciplinaId != null && disciplinaId > 0) {
        Disciplina disciplina = disciplinaRepository.findById(disciplinaId)
                .orElseThrow(() -> new ResourceNotFoundException("Disciplina não encontrada. Id: " + disciplinaId));
        turma.setDisciplina(disciplina);
    }

    Turma atualizada = turmaRepository.save(turma);
    return new TurmaResponseDTO(atualizada);
}
    @Transactional
    public void excluir(Long id) {
        Turma turma = turmaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Turma não encontrada. Id: " + id));

        if (turma.getUsuarios() != null && !turma.getUsuarios().isEmpty()) {
            throw new BusinessException("Não é possível excluir a turma pois ela possui alunos vinculados.");
        }

        List<ProfessorTurma> professores = professorTurmaRepository.findByTurmaId(id);
        if (!professores.isEmpty()) {
            professorTurmaRepository.deleteAll(professores);
        }

        turmaRepository.deleteById(id);
    }
}