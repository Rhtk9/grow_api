package br.cefetmg.grow.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.cefetmg.grow.model.EvolucaoFase;

public interface EvolucaoFaseRepository extends JpaRepository<EvolucaoFase, Long> {
}