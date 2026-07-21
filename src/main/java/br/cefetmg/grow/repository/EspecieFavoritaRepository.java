package br.cefetmg.grow.repository;

import br.cefetmg.grow.model.EspecieFavorita;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EspecieFavoritaRepository extends JpaRepository<EspecieFavorita, Long> {

    // Verifica se um usuário já favoritou uma espécie
    boolean existsByUsuarioIdAndEspeciePlantaId(Long usuarioId, Long especieId);

    // Busca todos os favoritos de um usuário
    List<EspecieFavorita> findByUsuarioId(Long usuarioId);

    // Busca favorito específico (para deletar)
    Optional<EspecieFavorita> findByUsuarioIdAndEspeciePlantaId(Long usuarioId, Long especieId);
}