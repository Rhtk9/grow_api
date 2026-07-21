package br.cefetmg.grow.service;

import br.cefetmg.grow.dto.ConteudoRequestDTO;
import br.cefetmg.grow.dto.ConteudoResponseDTO;
import br.cefetmg.grow.exception.ResourceNotFoundException;
import br.cefetmg.grow.model.Conteudo;
import br.cefetmg.grow.model.Disciplina;
import br.cefetmg.grow.repository.ConteudoRepository;
import br.cefetmg.grow.repository.DisciplinaRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ConteudoService {

    private final ConteudoRepository conteudoRepository;
    private final DisciplinaRepository disciplinaRepository;
    private final ModelMapper modelMapper;

    @Transactional(readOnly = true)
    public List<ConteudoResponseDTO> listarTodos() {
        return conteudoRepository.findAll().stream()
                .map(ConteudoResponseDTO::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<ConteudoResponseDTO> listarPorDisciplina(Long disciplinaId) {
        if (!disciplinaRepository.existsById(disciplinaId)) {
            throw new ResourceNotFoundException("Disciplina não encontrada. Id: " + disciplinaId);
        }
        return conteudoRepository.findByDisciplinaId(disciplinaId).stream()
                .map(ConteudoResponseDTO::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<ConteudoResponseDTO> listarPublicadosPorDisciplina(Long disciplinaId) {
        return conteudoRepository.findPublicadosPorDisciplina(disciplinaId).stream()
                .map(ConteudoResponseDTO::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<ConteudoResponseDTO> listarPublicados() {
        return conteudoRepository.findByPublicadoTrue().stream()
                .map(ConteudoResponseDTO::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public ConteudoResponseDTO buscarPorId(Long id) {
        Conteudo conteudo = conteudoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Conteúdo não encontrado. Id: " + id));
        return new ConteudoResponseDTO(conteudo);
    }

    @Transactional
    public ConteudoResponseDTO criar(ConteudoRequestDTO dto) {
        Disciplina disciplina = disciplinaRepository.findById(dto.getDisciplinaId())
                .orElseThrow(() -> new ResourceNotFoundException("Disciplina não encontrada. Id: " + dto.getDisciplinaId()));

        Conteudo conteudo = modelMapper.map(dto, Conteudo.class);
        conteudo.setDisciplina(disciplina);
        conteudo.setDataCriacao(LocalDateTime.now());
        if (dto.getPublicado() == null) {
            conteudo.setPublicado(false);
        }

        Conteudo salvo = conteudoRepository.save(conteudo);
        return new ConteudoResponseDTO(salvo);
    }

    @Transactional
    public ConteudoResponseDTO atualizar(Long id, ConteudoRequestDTO dto) {
        Conteudo conteudo = conteudoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Conteúdo não encontrado. Id: " + id));

        Disciplina disciplina = disciplinaRepository.findById(dto.getDisciplinaId())
                .orElseThrow(() -> new ResourceNotFoundException("Disciplina não encontrada. Id: " + dto.getDisciplinaId()));

        modelMapper.map(dto, conteudo);
        conteudo.setId(id);
        conteudo.setDisciplina(disciplina);

        Conteudo atualizado = conteudoRepository.save(conteudo);
        return new ConteudoResponseDTO(atualizado);
    }

    @Transactional
    public void excluir(Long id) {
        if (!conteudoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Conteúdo não encontrado. Id: " + id);
        }
        conteudoRepository.deleteById(id);
    }

    @Transactional
    public ConteudoResponseDTO publicar(Long id) {
        Conteudo conteudo = conteudoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Conteúdo não encontrado. Id: " + id));
        
        conteudo.setPublicado(true);
        Conteudo atualizado = conteudoRepository.save(conteudo);
        return new ConteudoResponseDTO(atualizado);
    }

    @Transactional
    public ConteudoResponseDTO despublicar(Long id) {
        Conteudo conteudo = conteudoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Conteúdo não encontrado. Id: " + id));
        
        conteudo.setPublicado(false);
        Conteudo atualizado = conteudoRepository.save(conteudo);
        return new ConteudoResponseDTO(atualizado);
    }
}