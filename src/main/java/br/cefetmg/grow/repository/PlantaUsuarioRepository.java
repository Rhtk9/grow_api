package br.cefetmg.grow.repository;

import br.cefetmg.grow.model.PlantaUsuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlantaUsuarioRepository extends JpaRepository<PlantaUsuario, Long> {

    List<PlantaUsuario> findByUsuarioId(Long usuarioId);

    List<PlantaUsuario> findByUsuarioIdAndFaseId(Long usuarioId, Long faseId);

    List<PlantaUsuario> findByEspeciePlantaId(Long especieId);
}