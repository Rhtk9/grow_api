package br.cefetmg.grow.repository;

import br.cefetmg.grow.model.Recompensa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecompensaRepository extends JpaRepository<Recompensa, Long> {
    
    List<Recompensa> findByRaridade(String raridade);
    
    List<Recompensa> findByXpNecessarioLessThanEqual(Integer xp);
    
    List<Recompensa> findByTipo(String tipo);
}