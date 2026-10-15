package br.cefetmg.grow.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.cefetmg.grow.model.Avaliacao;

@Repository
public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Long> {
    
    @Query("SELECT a FROM Avaliacao a WHERE a.especie.id = :especieId")
    List<Avaliacao> findByEspecieCdEspecie(@Param("especieId") Long especieId);
    
    @Query("SELECT a FROM Avaliacao a WHERE a.usuario.id = :usuarioId")
    List<Avaliacao> findByUsuarioCdUsuario(@Param("usuarioId") Long usuarioId);
    
    @Query("SELECT a FROM Avaliacao a WHERE a.especie.id = :especieId AND a.usuario.id = :usuarioId")
    Optional<Avaliacao> findByEspecieCdEspecieAndUsuarioCdUsuario(@Param("especieId") Long especieId, @Param("usuarioId") Long usuarioId);
    
    List<Avaliacao> findByNotaGreaterThanEqual(Integer nota);
}