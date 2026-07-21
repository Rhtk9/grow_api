package br.cefetmg.grow.repository;

import br.cefetmg.grow.model.FasePlanta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface FasePlantaRepository extends JpaRepository<FasePlanta, Long> {

    // Busca as fases pelo ID da EspeciePlanta (referenciando o atributo 'id' dela)
    @Query("SELECT f FROM FasePlanta f WHERE f.especiePlanta.id = :especieId")
    List<FasePlanta> findByEspecieId(@Param("especieId") Long especieId);

    // Verifica se já existe uma fase com o mesmo nome para aquela espécie
    @Query("SELECT COUNT(f) > 0 FROM FasePlanta f WHERE f.nome = :nome AND f.especiePlanta.id = :especieId")
    boolean existsByNomeAndEspecieId(@Param("nome") String nome, @Param("especieId") Long especieId);

    // Verifica duplicidade excluindo a fase que está sendo atualizada (pelo ID dela)
    @Query("SELECT COUNT(f) > 0 FROM FasePlanta f WHERE f.nome = :nome AND f.especiePlanta.id = :especieId AND f.id <> :faseId")
    boolean existsByNomeAndEspecieIdAndIdNot(@Param("nome") String nome, @Param("especieId") Long especieId, @Param("faseId") Long faseId);
}