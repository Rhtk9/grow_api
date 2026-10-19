package br.cefetmg.grow.service;

import br.cefetmg.grow.dto.UsuarioTarefaRequestDTO;
import br.cefetmg.grow.dto.UsuarioTarefaResponseDTO;
import br.cefetmg.grow.exception.BusinessException;
import br.cefetmg.grow.exception.ResourceNotFoundException;
import br.cefetmg.grow.model.Atividade;
import br.cefetmg.grow.model.Usuario;
import br.cefetmg.grow.model.UsuarioTarefa;
import br.cefetmg.grow.repository.AtividadeRepository;
import br.cefetmg.grow.repository.UsuarioRepository;
import br.cefetmg.grow.repository.UsuarioTarefaRepository;
import br.cefetmg.grow.repository.UsuarioTurmaRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioTarefaService {

    private final UsuarioTarefaRepository usuarioTarefaRepository;
    private final UsuarioRepository usuarioRepository;
    private final AtividadeRepository atividadeRepository;
    private final UsuarioTurmaRepository usuarioTurmaRepository;
    private final ModelMapper modelMapper;

    @Transactional(readOnly = true)
    public List<UsuarioTarefaResponseDTO> listarTodos() {
        return usuarioTarefaRepository.findAll().stream()
                .map(UsuarioTarefaResponseDTO::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<UsuarioTarefaResponseDTO> listarPorUsuario(Long usuarioId) {
        if (!usuarioRepository.existsById(usuarioId)) {
            throw new ResourceNotFoundException("Usuário não encontrado. Id: " + usuarioId);
        }
        return usuarioTarefaRepository.findByUsuarioId(usuarioId).stream()
                .map(UsuarioTarefaResponseDTO::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<UsuarioTarefaResponseDTO> listarPorAtividade(Long atividadeId) {
        if (!atividadeRepository.existsById(atividadeId)) {
            throw new ResourceNotFoundException("Atividade não encontrada. Id: " + atividadeId);
        }
        return usuarioTarefaRepository.findByAtividadeId(atividadeId).stream()
                .map(UsuarioTarefaResponseDTO::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<UsuarioTarefaResponseDTO> listarConcluidas() {
        return usuarioTarefaRepository.findByConcluidaTrue().stream()
                .map(UsuarioTarefaResponseDTO::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<UsuarioTarefaResponseDTO> listarNaoConcluidas() {
        return usuarioTarefaRepository.findByConcluidaFalse().stream()
                .map(UsuarioTarefaResponseDTO::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public UsuarioTarefaResponseDTO buscarPorId(Long id) {
        UsuarioTarefa usuarioTarefa = usuarioTarefaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tarefa do usuário não encontrada. Id: " + id));
        return new UsuarioTarefaResponseDTO(usuarioTarefa);
    }

    @Transactional
    public UsuarioTarefaResponseDTO criar(UsuarioTarefaRequestDTO dto) {
        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado. Id: " + dto.getUsuarioId()));

        Atividade atividade = atividadeRepository.findById(dto.getAtividadeId())
                .orElseThrow(() -> new ResourceNotFoundException("Atividade não encontrada. Id: " + dto.getAtividadeId()));

        UsuarioTarefa usuarioTarefa = modelMapper.map(dto, UsuarioTarefa.class);
        usuarioTarefa.setUsuario(usuario);
        usuarioTarefa.setAtividade(atividade);
        usuarioTarefa.setDataConclusao(LocalDate.now());

        UsuarioTarefa salvo = usuarioTarefaRepository.save(usuarioTarefa);
        return new UsuarioTarefaResponseDTO(salvo);
    }

    @Transactional
    public UsuarioTarefaResponseDTO atualizar(Long id, UsuarioTarefaRequestDTO dto) {
        UsuarioTarefa usuarioTarefa = usuarioTarefaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tarefa do usuário não encontrada. Id: " + id));

        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado. Id: " + dto.getUsuarioId()));

        Atividade atividade = atividadeRepository.findById(dto.getAtividadeId())
                .orElseThrow(() -> new ResourceNotFoundException("Atividade não encontrada. Id: " + dto.getAtividadeId()));

        modelMapper.map(dto, usuarioTarefa);
        usuarioTarefa.setId(id);
        usuarioTarefa.setUsuario(usuario);
        usuarioTarefa.setAtividade(atividade);
        usuarioTarefa.setDataConclusao(LocalDate.now());

        UsuarioTarefa atualizado = usuarioTarefaRepository.save(usuarioTarefa);
        return new UsuarioTarefaResponseDTO(atualizado);
    }

    @Transactional
    public void excluir(Long id) {
        if (!usuarioTarefaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Tarefa do usuário não encontrada. Id: " + id);
        }
        usuarioTarefaRepository.deleteById(id);
    }

    @Transactional
    public UsuarioTarefaResponseDTO concluirTarefa(Long id, BigDecimal nota, String feedback) {
        UsuarioTarefa usuarioTarefa = usuarioTarefaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tarefa do usuário não encontrada. Id: " + id));
        
        usuarioTarefa.setConcluida(true);
        usuarioTarefa.setNota(nota);
        usuarioTarefa.setFeedback(feedback);
        usuarioTarefa.setDataConclusao(LocalDate.now());
        
        UsuarioTarefa atualizado = usuarioTarefaRepository.save(usuarioTarefa);
        return new UsuarioTarefaResponseDTO(atualizado);
    }

    /**
     * ALUNO "pega" a atividade: só é permitido se ele estiver na turma da atividade.
     */
    @Transactional
    public UsuarioTarefaResponseDTO pegar(Usuario aluno, Long atividadeId) {
        Atividade atividade = atividadeRepository.findById(atividadeId)
                .orElseThrow(() -> new ResourceNotFoundException("Atividade não encontrada. Id: " + atividadeId));

        Long turmaId = atividade.getTurma().getId();
        if (!usuarioTurmaRepository.existsByUsuarioIdAndTurmaId(aluno.getId(), turmaId)) {
            throw new AccessDeniedException("Você precisa estar na turma desta atividade para pegá-la");
        }
        if (usuarioTarefaRepository.existsByUsuarioIdAndAtividadeId(aluno.getId(), atividadeId)) {
            throw new BusinessException("Você já pegou esta atividade");
        }

        Usuario usuario = usuarioRepository.findById(aluno.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado. Id: " + aluno.getId()));

        UsuarioTarefa tarefa = new UsuarioTarefa();
        tarefa.setUsuario(usuario);
        tarefa.setAtividade(atividade);
        tarefa.setNota(BigDecimal.ZERO);
        tarefa.setConcluida(false);
        tarefa.setTempoGasto(0);
        tarefa.setTentativas(0);
        tarefa.setXpRecebido(0);
        tarefa.setDataConclusao(LocalDate.now());

        return new UsuarioTarefaResponseDTO(usuarioTarefaRepository.save(tarefa));
    }

    /**
     * ALUNO entrega a atividade: só é permitido se ele já tiver pegado a atividade.
     * A nota NÃO é definida pelo aluno (só o professor, em /concluir).
     */
    @Transactional
    public UsuarioTarefaResponseDTO entregar(Usuario aluno, UsuarioTarefaRequestDTO dto) {
        if (!aluno.getId().equals(dto.getUsuarioId())) {
            throw new AccessDeniedException("Você só pode entregar atividades em seu próprio nome");
        }

        UsuarioTarefa tarefa = usuarioTarefaRepository
                .findByUsuarioIdAndAtividadeId(aluno.getId(), dto.getAtividadeId())
                .orElseThrow(() -> new BusinessException("Você precisa pegar a atividade antes de entregá-la"));

        if (Boolean.TRUE.equals(tarefa.getConcluida())) {
            throw new BusinessException("Esta atividade já foi corrigida pelo professor");
        }

        Integer limite = tarefa.getAtividade().getTentativas();
        int usadas = tarefa.getTentativas() == null ? 0 : tarefa.getTentativas();
        if (limite != null && usadas >= limite) {
            throw new BusinessException("Limite de tentativas desta atividade atingido");
        }

        tarefa.setTempoGasto(dto.getTempoGasto());
        tarefa.setTentativas(usadas + 1);
        tarefa.setDataConclusao(LocalDate.now());

        return new UsuarioTarefaResponseDTO(usuarioTarefaRepository.save(tarefa));
    }
}
