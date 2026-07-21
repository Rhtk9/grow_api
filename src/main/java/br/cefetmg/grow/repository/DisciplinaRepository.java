package br.cefetmg.grow.repository;

import br.cefetmg.grow.model.Disciplina;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DisciplinaRepository extends JpaRepository<Disciplina, Long> {
    
    Optional<Disciplina> findByNome(String nome);
    
    List<Disciplina> findByAtivaTrue();
    
    List<Disciplina> findByAtivaFalse();
    
    List<Disciplina> findAllByOrderByNomeAsc();
    
    boolean existsByNome(String nome);
}