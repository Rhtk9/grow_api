package br.cefetmg.grow.repository;

import br.cefetmg.grow.model.ProfessorTurma;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProfessorTurmaRepository extends JpaRepository<ProfessorTurma, Long> {
    
    List<ProfessorTurma> findByUsuarioId(Long usuarioId);
    
    List<ProfessorTurma> findByTurmaId(Long turmaId);
}