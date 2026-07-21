package br.cefetmg.grow.service;

import br.cefetmg.grow.dto.RelatorioRequestDTO;
import br.cefetmg.grow.dto.RelatorioResponseDTO;
import br.cefetmg.grow.exception.ResourceNotFoundException;
import br.cefetmg.grow.model.Relatorio;
import br.cefetmg.grow.model.Usuario;
import br.cefetmg.grow.repository.RelatorioRepository;
import br.cefetmg.grow.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RelatorioService {

    private final RelatorioRepository relatorioRepository;
    private final UsuarioRepository usuarioRepository;
    private final ModelMapper modelMapper;

    @Transactional(readOnly = true)
    public List<RelatorioResponseDTO> listarTodos() {
        return relatorioRepository.findAll().stream()
                .map(RelatorioResponseDTO::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<RelatorioResponseDTO> listarPorUsuario(Long usuarioId) {
        if (!usuarioRepository.existsById(usuarioId)) {
            throw new ResourceNotFoundException("Usuário não encontrado. Id: " + usuarioId);
        }
        return relatorioRepository.findByUsuarioId(usuarioId).stream()
                .map(RelatorioResponseDTO::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public RelatorioResponseDTO buscarUltimoPorUsuario(Long usuarioId) {
        Relatorio relatorio = relatorioRepository.findTopByUsuarioIdOrderByDataAtualizacaoDesc(usuarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Nenhum relatório encontrado para o usuário: " + usuarioId));
        return new RelatorioResponseDTO(relatorio);
    }

    @Transactional(readOnly = true)
    public RelatorioResponseDTO buscarPorId(Long id) {
        Relatorio relatorio = relatorioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Relatório não encontrado. Id: " + id));
        return new RelatorioResponseDTO(relatorio);
    }

    @Transactional
    public RelatorioResponseDTO criar(RelatorioRequestDTO dto) {
        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado. Id: " + dto.getUsuarioId()));

        Relatorio relatorio = modelMapper.map(dto, Relatorio.class);
        relatorio.setUsuario(usuario);
        relatorio.setDataAtualizacao(LocalDateTime.now());

        Relatorio salvo = relatorioRepository.save(relatorio);
        return new RelatorioResponseDTO(salvo);
    }

    @Transactional
    public RelatorioResponseDTO atualizar(Long id, RelatorioRequestDTO dto) {
        Relatorio relatorio = relatorioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Relatório não encontrado. Id: " + id));

        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado. Id: " + dto.getUsuarioId()));

        modelMapper.map(dto, relatorio);
        relatorio.setId(id);
        relatorio.setUsuario(usuario);
        relatorio.setDataAtualizacao(LocalDateTime.now());

        Relatorio atualizado = relatorioRepository.save(relatorio);
        return new RelatorioResponseDTO(atualizado);
    }

    @Transactional
    public void excluir(Long id) {
        if (!relatorioRepository.existsById(id)) {
            throw new ResourceNotFoundException("Relatório não encontrado. Id: " + id);
        }
        relatorioRepository.deleteById(id);
    }
}