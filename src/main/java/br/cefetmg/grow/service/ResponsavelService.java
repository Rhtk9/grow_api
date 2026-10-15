package br.cefetmg.grow.service;

import br.cefetmg.grow.dto.ResponsavelRequestDTO;
import br.cefetmg.grow.dto.ResponsavelResponseDTO;
import br.cefetmg.grow.exception.ResourceNotFoundException;
import br.cefetmg.grow.model.Responsavel;
import br.cefetmg.grow.model.Usuario;
import br.cefetmg.grow.repository.ResponsavelRepository;
import br.cefetmg.grow.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ResponsavelService {

    private final ResponsavelRepository responsavelRepository;
    private final UsuarioRepository usuarioRepository;
    private final ModelMapper modelMapper;

    @Transactional(readOnly = true)
    public List<ResponsavelResponseDTO> listarTodos() {
        return responsavelRepository.findAll().stream()
                .map(ResponsavelResponseDTO::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<ResponsavelResponseDTO> listarPorUsuario(Long usuarioId) {
        if (!usuarioRepository.existsById(usuarioId)) {
            throw new ResourceNotFoundException("Usuário não encontrado. Id: " + usuarioId);
        }
        return responsavelRepository.findByUsuarioId(usuarioId).stream()
                .map(ResponsavelResponseDTO::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<ResponsavelResponseDTO> listarComNotificacoesAtivas() {
        return responsavelRepository.findByNotificacoesTrue().stream()
                .map(ResponsavelResponseDTO::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public ResponsavelResponseDTO buscarPorId(Long id) {
        Responsavel responsavel = responsavelRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Responsável não encontrado. Id: " + id));
        return new ResponsavelResponseDTO(responsavel);
    }

        @Transactional
    public ResponsavelResponseDTO criar(ResponsavelRequestDTO dto) {
        Usuario aluno = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new ResourceNotFoundException("Aluno não encontrado. Id: " + dto.getUsuarioId()));

        Usuario responsavelUsuario = usuarioRepository.findById(dto.getResponsavelId())
                .orElseThrow(() -> new ResourceNotFoundException("Responsável não encontrado. Id: " + dto.getResponsavelId()));

        Responsavel responsavel = new Responsavel();
        responsavel.setParentesco(dto.getParentesco());
        responsavel.setNotificacoes(dto.getNotificacoes());
        responsavel.setUsuario(aluno);
        responsavel.setResponsavel(responsavelUsuario);
        responsavel.setDataVinculo(LocalDate.now());

        Responsavel salvo = responsavelRepository.save(responsavel);
        return new ResponsavelResponseDTO(salvo);
    }

    @Transactional
    public ResponsavelResponseDTO atualizar(Long id, ResponsavelRequestDTO dto) {
        Responsavel responsavel = responsavelRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Responsável não encontrado. Id: " + id));

        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado. Id: " + dto.getUsuarioId()));

        modelMapper.map(dto, responsavel);
        responsavel.setId(id);
        responsavel.setUsuario(usuario);

        Responsavel atualizado = responsavelRepository.save(responsavel);
        return new ResponsavelResponseDTO(atualizado);
    }

    @Transactional
    public void excluir(Long id) {
        if (!responsavelRepository.existsById(id)) {
            throw new ResourceNotFoundException("Responsável não encontrado. Id: " + id);
        }
        responsavelRepository.deleteById(id);
    }

    @Transactional
    public ResponsavelResponseDTO atualizarNotificacoes(Long id, Boolean notificacoes) {
        Responsavel responsavel = responsavelRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Responsável não encontrado. Id: " + id));
        
        responsavel.setNotificacoes(notificacoes);
        Responsavel atualizado = responsavelRepository.save(responsavel);
        return new ResponsavelResponseDTO(atualizado);
    }

        @Transactional(readOnly = true)
    public List<ResponsavelResponseDTO> listarPorResponsavel(Long responsavelId) {
        if (!usuarioRepository.existsById(responsavelId)) {
            throw new ResourceNotFoundException("Usuário responsável não encontrado. Id: " + responsavelId);
        }
        return responsavelRepository.findByResponsavelId(responsavelId).stream()
                .map(ResponsavelResponseDTO::new)
                .toList();
    }

    @Transactional
    public void excluirVinculo(Long responsavelId, Long alunoId) {
        Responsavel vinculo = responsavelRepository.findByResponsavelIdAndUsuarioId(responsavelId, alunoId)
                .orElseThrow(() -> new ResourceNotFoundException("Vínculo não encontrado"));
        responsavelRepository.delete(vinculo);
    }
}