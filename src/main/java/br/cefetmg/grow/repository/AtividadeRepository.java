package br.cefetmg.grow.repository;

import br.cefetmg.grow.model.Atividade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface AtividadeRepository extends JpaRepository<Atividade, Long> {
    
    // Métodos simples
    List<Atividade> findByTurmaId(Long turmaId);
    
    List<Atividade> findByDataEntregaBefore(LocalDate data);
    
    List<Atividade> findByDataEntregaAfter(LocalDate data);
    
    List<Atividade> findByStatus(String status);
    
    // Queries mais complexas (precisam de @Query)
    @Query("SELECT a FROM Atividade a WHERE a.turma.id = :turmaId AND a.dataEntrega >= :dataAtual ORDER BY a.dataEntrega ASC")
    List<Atividade> findAtividadesFuturasPorTurma(@Param("turmaId") Long turmaId, @Param("dataAtual") LocalDate dataAtual);
    
    @Query("SELECT a FROM Atividade a WHERE a.turma.id = :turmaId AND a.dataEntrega < :dataAtual ORDER BY a.dataEntrega DESC")
    List<Atividade> findAtividadesPassadasPorTurma(@Param("turmaId") Long turmaId, @Param("dataAtual") LocalDate dataAtual);
    
    @Query("SELECT a FROM Atividade a JOIN a.turma t JOIN t.usuarios u WHERE u.usuario.id = :usuarioId")
    List<Atividade> findAtividadesPorUsuario(@Param("usuarioId") Long usuarioId);
}