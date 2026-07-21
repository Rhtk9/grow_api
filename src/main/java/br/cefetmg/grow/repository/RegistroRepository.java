package br.cefetmg.grow.repository;

import br.cefetmg.grow.model.Registro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface RegistroRepository extends JpaRepository<Registro, Long> {
    
    List<Registro> findByPlantaUsuarioId(Long plantaUsuarioId);
    
    List<Registro> findByDataRegistro(LocalDate data);
    
    List<Registro> findByDataRegistroBetween(LocalDate inicio, LocalDate fim);
}