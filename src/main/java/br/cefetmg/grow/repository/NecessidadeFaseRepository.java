package br.cefetmg.grow.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.cefetmg.grow.model.NecessidadeFase;

public interface NecessidadeFaseRepository extends JpaRepository<NecessidadeFase, Long> {
}