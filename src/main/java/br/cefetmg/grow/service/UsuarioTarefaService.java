package br.cefetmg.grow.service;

import br.cefetmg.grow.dto.UsuarioTarefaRequestDTO;
import br.cefetmg.grow.dto.UsuarioTarefaResponseDTO;
import br.cefetmg.grow.exception.ResourceNotFoundException;
import br.cefetmg.grow.model.Atividade;
import br.cefetmg.grow.model.Usuario;
import br.cefetmg.grow.model.UsuarioTarefa;
import br.cefetmg.grow.repository.AtividadeRepository;
import br.cefetmg.grow.repository.UsuarioRepository;
import br.cefetmg.grow.repository.UsuarioTarefaRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
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
}