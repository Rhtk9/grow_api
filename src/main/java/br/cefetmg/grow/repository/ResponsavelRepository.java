package br.cefetmg.grow.repository;

import br.cefetmg.grow.model.Responsavel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ResponsavelRepository extends JpaRepository<Responsavel, Long> {

    List<Responsavel> findByUsuarioId(Long usuarioId);

    // NOVO: busca os vínculos de um responsável específico
    List<Responsavel> findByResponsavelId(Long responsavelId);

    // NOVO: acha um vínculo específico responsável+aluno (pra desvincular)
    Optional<Responsavel> findByResponsavelIdAndUsuarioId(Long responsavelId, Long usuarioId);

    List<Responsavel> findByNotificacoesTrue();
}