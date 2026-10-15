package br.cefetmg.grow.service;

import br.cefetmg.grow.dto.UsuarioTurmaRequestDTO;
import br.cefetmg.grow.dto.UsuarioTurmaResponseDTO;
import br.cefetmg.grow.exception.ResourceNotFoundException;
import br.cefetmg.grow.model.Turma;
import br.cefetmg.grow.model.Usuario;
import br.cefetmg.grow.model.UsuarioTurma;
import br.cefetmg.grow.repository.TurmaRepository;
import br.cefetmg.grow.repository.UsuarioRepository;
import br.cefetmg.grow.repository.UsuarioTurmaRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioTurmaService {

    private final UsuarioTurmaRepository usuarioTurmaRepository;
    private final UsuarioRepository usuarioRepository;
    private final TurmaRepository turmaRepository;
    private final ModelMapper modelMapper;

    @Transactional(readOnly = true)
    public List<UsuarioTurmaResponseDTO> listarTodos() {
        return usuarioTurmaRepository.findAll().stream()
                .map(UsuarioTurmaResponseDTO::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<UsuarioTurmaResponseDTO> listarPorUsuario(Long usuarioId) {
        if (!usuarioRepository.existsById(usuarioId)) {
            throw new ResourceNotFoundException("Usuário não encontrado. Id: " + usuarioId);
        }
        return usuarioTurmaRepository.findByUsuarioId(usuarioId).stream()
                .map(UsuarioTurmaResponseDTO::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<UsuarioTurmaResponseDTO> listarPorTurma(Long turmaId) {
        if (!turmaRepository.existsById(turmaId)) {
            throw new ResourceNotFoundException("Turma não encontrada. Id: " + turmaId);
        }
        return usuarioTurmaRepository.findByTurmaId(turmaId).stream()
                .map(UsuarioTurmaResponseDTO::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<UsuarioTurmaResponseDTO> listarAtivos() {
        return usuarioTurmaRepository.findByAtivoTrue().stream()
                .map(UsuarioTurmaResponseDTO::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<UsuarioTurmaResponseDTO> listarFavoritos() {
        return usuarioTurmaRepository.findByFavoritoTrue().stream()
                .map(UsuarioTurmaResponseDTO::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public UsuarioTurmaResponseDTO buscarPorId(Long id) {
        UsuarioTurma usuarioTurma = usuarioTurmaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vínculo usuário-turma não encontrado. Id: " + id));
        return new UsuarioTurmaResponseDTO(usuarioTurma);
    }

    @Transactional
    public UsuarioTurmaResponseDTO criar(UsuarioTurmaRequestDTO dto) {
        // Verifica se o vínculo já existe
        if (usuarioTurmaRepository.existsByUsuarioIdAndTurmaId(dto.getUsuarioId(), dto.getTurmaId())) {
            throw new IllegalArgumentException("Usuário já está vinculado a esta turma");
        }

        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado. Id: " + dto.getUsuarioId()));

        Turma turma = turmaRepository.findById(dto.getTurmaId())
                .orElseThrow(() -> new ResourceNotFoundException("Turma não encontrada. Id: " + dto.getTurmaId()));

        UsuarioTurma usuarioTurma = modelMapper.map(dto, UsuarioTurma.class);
        usuarioTurma.setUsuario(usuario);
        usuarioTurma.setTurma(turma);
        usuarioTurma.setDataEntrada(LocalDate.now());

        UsuarioTurma salvo = usuarioTurmaRepository.save(usuarioTurma);
        return new UsuarioTurmaResponseDTO(salvo);
    }

    @Transactional
    public UsuarioTurmaResponseDTO atualizar(Long id, UsuarioTurmaRequestDTO dto) {
        UsuarioTurma usuarioTurma = usuarioTurmaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vínculo usuário-turma não encontrado. Id: " + id));

        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado. Id: " + dto.getUsuarioId()));

        Turma turma = turmaRepository.findById(dto.getTurmaId())
                .orElseThrow(() -> new ResourceNotFoundException("Turma não encontrada. Id: " + dto.getTurmaId()));

        modelMapper.map(dto, usuarioTurma);
        usuarioTurma.setId(id);
        usuarioTurma.setUsuario(usuario);
        usuarioTurma.setTurma(turma);

        UsuarioTurma atualizado = usuarioTurmaRepository.save(usuarioTurma);
        return new UsuarioTurmaResponseDTO(atualizado);
    }

    @Transactional
    public void excluir(Long id) {
        if (!usuarioTurmaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Vínculo usuário-turma não encontrado. Id: " + id);
        }
        usuarioTurmaRepository.deleteById(id);
    }

    @Transactional
    public UsuarioTurmaResponseDTO ativarDesativar(Long id, Boolean ativo) {
        UsuarioTurma usuarioTurma = usuarioTurmaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vínculo usuário-turma não encontrado. Id: " + id));
        
        usuarioTurma.setAtivo(ativo);
        UsuarioTurma atualizado = usuarioTurmaRepository.save(usuarioTurma);
        return new UsuarioTurmaResponseDTO(atualizado);
    }

    @Transactional
    public UsuarioTurmaResponseDTO favoritarDesfavoritar(Long id, Boolean favorito) {
        UsuarioTurma usuarioTurma = usuarioTurmaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vínculo usuário-turma não encontrado. Id: " + id));
        
        usuarioTurma.setFavorito(favorito);
        UsuarioTurma atualizado = usuarioTurmaRepository.save(usuarioTurma);
        return new UsuarioTurmaResponseDTO(atualizado);
    }
}
