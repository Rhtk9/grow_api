package br.cefetmg.grow.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.cefetmg.grow.model.PlantaUsuario;

public interface PlantaUsuarioRepository extends JpaRepository<PlantaUsuario, Long> {

    List<PlantaUsuario> findByUsuarioId(Long usuarioId);

    List<PlantaUsuario> findByUsuarioIdAndFaseId(Long usuarioId, Long faseId);

    List<PlantaUsuario> findByEspeciePlantaId(Long especieId);
}