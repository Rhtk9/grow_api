package br.cefetmg.grow.repository;

import br.cefetmg.grow.model.UsuarioTurma;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UsuarioTurmaRepository extends JpaRepository<UsuarioTurma, Long> {
    
    List<UsuarioTurma> findByUsuarioId(Long usuarioId);
    
    List<UsuarioTurma> findByTurmaId(Long turmaId);
    
    List<UsuarioTurma> findByAtivoTrue();
    
    List<UsuarioTurma> findByFavoritoTrue();
    
    boolean existsByUsuarioIdAndTurmaId(Long usuarioId, Long turmaId);
}