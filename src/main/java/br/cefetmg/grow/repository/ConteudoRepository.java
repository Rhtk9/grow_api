package br.cefetmg.grow.repository;

import br.cefetmg.grow.model.Conteudo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ConteudoRepository extends JpaRepository<Conteudo, Long> {
    
    
    List<Conteudo> findByDisciplinaId(Long disciplinaId);
    
    List<Conteudo> findByPublicadoTrue();
    
    List<Conteudo> findByPublicadoFalse();
    
    List<Conteudo> findByTipo(String tipo);
    
    List<Conteudo> findByNivel(Integer nivel);
    
    List<Conteudo> findByTituloContaining(String titulo);
    
  
    @Query("SELECT c FROM Conteudo c WHERE c.disciplina.id = :disciplinaId AND c.publicado = true")
    List<Conteudo> findPublicadosPorDisciplina(@Param("disciplinaId") Long disciplinaId);
}