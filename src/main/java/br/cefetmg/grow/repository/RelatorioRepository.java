package br.cefetmg.grow.repository;

import br.cefetmg.grow.model.Relatorio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RelatorioRepository extends JpaRepository<Relatorio, Long> {
    
    List<Relatorio> findByUsuarioId(Long usuarioId);
    
    Optional<Relatorio> findTopByUsuarioIdOrderByDataAtualizacaoDesc(Long usuarioId);
}