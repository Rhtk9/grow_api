package br.cefetmg.grow.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "TbProfessorTurma")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProfessorTurma {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CdProfessor")
    private Long id;

    @Column(name = "DtVinculo", nullable = false)
    private LocalDate dataVinculo;

    @Column(name = "Excluir")
    private Boolean excluir = false;

    @Column(name = "Edicao")
    private Boolean edicao = false;

    @Column(name = "GerenciarTarefas")
    private Boolean gerenciarTarefas = false;

    @ManyToOne
    @JoinColumn(name = "tbUsuario_CdUsuario", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "TbTurma_CdTurma", nullable = false)
    private Turma turma;
}