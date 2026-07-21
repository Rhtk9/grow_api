package br.cefetmg.grow.service;

import br.cefetmg.grow.dto.AtividadeRequestDTO;
import br.cefetmg.grow.dto.AtividadeResponseDTO;
import br.cefetmg.grow.exception.ResourceNotFoundException;
import br.cefetmg.grow.model.Atividade;
import br.cefetmg.grow.model.Turma;
import br.cefetmg.grow.repository.AtividadeRepository;
import br.cefetmg.grow.repository.TurmaRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AtividadeService {

    private final AtividadeRepository atividadeRepository;
    private final TurmaRepository turmaRepository;
    private final ModelMapper modelMapper;

    @Transactional(readOnly = true)
    public List<AtividadeResponseDTO> listarTodas() {
        return atividadeRepository.findAll().stream()
                .map(AtividadeResponseDTO::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<AtividadeResponseDTO> listarPorTurma(Long turmaId) {
        if (!turmaRepository.existsById(turmaId)) {
            throw new ResourceNotFoundException("Turma não encontrada. Id: " + turmaId);
        }
        return atividadeRepository.findByTurmaId(turmaId).stream()
                .map(AtividadeResponseDTO::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<AtividadeResponseDTO> listarAtividadesFuturasPorTurma(Long turmaId) {
        return atividadeRepository.findAtividadesFuturasPorTurma(turmaId, LocalDate.now()).stream()
                .map(AtividadeResponseDTO::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<AtividadeResponseDTO> listarAtividadesPassadasPorTurma(Long turmaId) {
        return atividadeRepository.findAtividadesPassadasPorTurma(turmaId, LocalDate.now()).stream()
                .map(AtividadeResponseDTO::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<AtividadeResponseDTO> listarPorUsuario(Long usuarioId) {
        return atividadeRepository.findAtividadesPorUsuario(usuarioId).stream()
                .map(AtividadeResponseDTO::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public AtividadeResponseDTO buscarPorId(Long id) {
        Atividade atividade = atividadeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Atividade não encontrada. Id: " + id));
        return new AtividadeResponseDTO(atividade);
    }

    @Transactional
    public AtividadeResponseDTO criar(AtividadeRequestDTO dto) {
        Turma turma = turmaRepository.findById(dto.getTurmaId())
                .orElseThrow(() -> new ResourceNotFoundException("Turma não encontrada. Id: " + dto.getTurmaId()));

        Atividade atividade = modelMapper.map(dto, Atividade.class);
        atividade.setTurma(turma);
        atividade.setDataCriacao(LocalDate.now());

        Atividade salva = atividadeRepository.save(atividade);
        return new AtividadeResponseDTO(salva);
    }

    @Transactional
    public AtividadeResponseDTO atualizar(Long id, AtividadeRequestDTO dto) {
        Atividade atividade = atividadeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Atividade não encontrada. Id: " + id));

        Turma turma = turmaRepository.findById(dto.getTurmaId())
                .orElseThrow(() -> new ResourceNotFoundException("Turma não encontrada. Id: " + dto.getTurmaId()));

        modelMapper.map(dto, atividade);
        atividade.setId(id);
        atividade.setTurma(turma);

        Atividade atualizada = atividadeRepository.save(atividade);
        return new AtividadeResponseDTO(atualizada);
    }

    @Transactional
    public void excluir(Long id) {
        if (!atividadeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Atividade não encontrada. Id: " + id);
        }
        atividadeRepository.deleteById(id);
    }

    @Transactional
    public AtividadeResponseDTO atualizarStatus(Long id, String status) {
        Atividade atividade = atividadeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Atividade não encontrada. Id: " + id));
        
        atividade.setStatus(status);
        Atividade atualizada = atividadeRepository.save(atividade);
        return new AtividadeResponseDTO(atualizada);
    }
}