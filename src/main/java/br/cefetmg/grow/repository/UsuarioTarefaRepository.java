package br.cefetmg.grow.repository;

import br.cefetmg.grow.model.UsuarioTarefa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UsuarioTarefaRepository extends JpaRepository<UsuarioTarefa, Long> {
    
    List<UsuarioTarefa> findByUsuarioId(Long usuarioId);
    
    List<UsuarioTarefa> findByAtividadeId(Long atividadeId);
    
    List<UsuarioTarefa> findByConcluidaTrue();
    
    List<UsuarioTarefa> findByConcluidaFalse();
}