package br.cefetmg.grow.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "TbAtividade")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Atividade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CdAtividade")
    private Long id;

    @Column(name = "Titulo", length = 100, nullable = false)
    private String titulo;

    @Column(name = "Descricao", length = 500, nullable = false)
    private String descricao;

    @Column(name = "Pontuacao", nullable = false)
    private Integer pontuacao;

    @Column(name = "Xp", nullable = false)
    private Integer xp;

    @Column(name = "DtCriacao", nullable = false)
    private LocalDate dataCriacao;

    @Column(name = "DtEntrega", nullable = false)
    private LocalDate dataEntrega;

    @Column(name = "Tentativas", nullable = false)
    private Integer tentativas;

    @Column(name = "Status", length = 45, nullable = false)
    private String status;

    @Column(name = "Dificuldade", nullable = false)
    private Integer dificuldade;

    @ManyToOne
    @JoinColumn(name = "TbTurma_CdTurma", nullable = false)
    private Turma turma;

    @OneToMany(mappedBy = "atividade", cascade = CascadeType.ALL)
    private List<UsuarioTarefa> usuarioTarefas;
}