package br.cefetmg.grow.repository;

import br.cefetmg.grow.model.UsuarioRecompensa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UsuarioRecompensaRepository extends JpaRepository<UsuarioRecompensa, Long> {
    
    List<UsuarioRecompensa> findByUsuarioId(Long usuarioId);
    
    List<UsuarioRecompensa> findByEquipadaTrue();
    
    @Query("SELECT ur FROM UsuarioRecompensa ur JOIN ur.usuario u WHERE u.id = :usuarioId AND ur.equipada = true")
    List<UsuarioRecompensa> findRecompensasEquipadasPorUsuario(@Param("usuarioId") Long usuarioId);
}