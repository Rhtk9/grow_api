package br.cefetmg.grow.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.cefetmg.grow.model.Registro;

public interface RegistroRepository extends JpaRepository<Registro, Long> {

    List<Registro> findByPlantaUsuarioId(Long plantaUsuarioId);

    List<Registro> findByDataRegistroBetween(LocalDateTime inicio, LocalDateTime fim);
}