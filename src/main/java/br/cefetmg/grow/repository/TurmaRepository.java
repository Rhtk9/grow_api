package br.cefetmg.grow.repository;

import br.cefetmg.grow.model.Turma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TurmaRepository extends JpaRepository<Turma, Long> {
    
    // Métodos simples - Spring Data JPA resolve automaticamente
    List<Turma> findByDisciplinaId(Long disciplinaId);
    
    Optional<Turma> findByCodigo(String codigo);
    
    List<Turma> findByAno(Integer ano);
    
    boolean existsByCodigo(String codigo);
    
    // Apenas este precisa de @Query porque envolve join com outra entidade
    @Query("SELECT t FROM Turma t JOIN t.usuarios u WHERE u.usuario.id = :usuarioId")
    List<Turma> findTurmasByUsuarioId(@Param("usuarioId") Long usuarioId);
}