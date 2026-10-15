package br.cefetmg.grow.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.cefetmg.grow.model.EspeciePlanta;

public interface EspeciePlantaRepository extends JpaRepository<EspeciePlanta, Long> {

    boolean existsByNomeCientifico(String nomeCientifico);

    List<EspeciePlanta> findByNomePopularContainingIgnoreCase(String nomePopular);

    List<EspeciePlanta> findByNomeCientificoContainingIgnoreCase(String nomeCientifico);

    List<EspeciePlanta> findByPublicaTrue();
}