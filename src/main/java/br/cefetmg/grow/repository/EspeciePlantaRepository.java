package br.cefetmg.grow.repository;

import br.cefetmg.grow.model.EspeciePlanta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EspeciePlantaRepository extends JpaRepository<EspeciePlanta, Long> {
    // Verificar se já existe uma espécie com o mesmo nome científico
    boolean existsByNomeCientifico(String nomeCientifico);
    
    // Buscar por nome popular (contendo)
    List<EspeciePlanta> findByNomePopularContainingIgnoreCase(String nomePopular);
    
    // Buscar por nome científico (contendo)
    List<EspeciePlanta> findByNomeCientificoContainingIgnoreCase(String nomeCientifico);
    
    // Buscar espécies públicas
    List<EspeciePlanta> findByPublicaTrue();
}