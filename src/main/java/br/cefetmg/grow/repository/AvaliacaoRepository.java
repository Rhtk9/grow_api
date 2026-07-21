package br.cefetmg.grow.repository;

import br.cefetmg.grow.model.Avaliacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Long> {
    
    List<Avaliacao> findByEspecieId(Long especieId);
    
    List<Avaliacao> findByUsuarioId(Long usuarioId);
    
    List<Avaliacao> findByNotaGreaterThanEqual(Integer nota);
}