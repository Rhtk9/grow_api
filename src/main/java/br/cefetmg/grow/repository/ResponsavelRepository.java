package br.cefetmg.grow.repository;

import br.cefetmg.grow.model.Responsavel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResponsavelRepository extends JpaRepository<Responsavel, Long> {
    
    List<Responsavel> findByUsuarioId(Long usuarioId);
    
    List<Responsavel> findByNotificacoesTrue();
}