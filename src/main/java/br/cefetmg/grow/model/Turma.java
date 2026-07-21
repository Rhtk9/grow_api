package br.cefetmg.grow.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "TbTurma")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Turma {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CdTurma")
    private Long id;

    @Column(name = "Nome", length = 100, nullable = false)
    private String nome;

    @Column(name = "Codigo", length = 20, nullable = false)
    private String codigo;

    @Column(name = "Descricao", length = 500, nullable = false)
    private String descricao;

    @Column(name = "Img", length = 255)
    private String imagem;

    @Column(name = "Ano", nullable = false)
    private Integer ano;

    @Column(name = "NivelEnsino", length = 100, nullable = false)
    private String nivelEnsino;

    @Column(name = "DtCriacao", nullable = false)
    private LocalDate dataCriacao;

    @ManyToOne
    @JoinColumn(name = "TbDisciplina_CdDisciplina", nullable = false)
    private Disciplina disciplina;

    @OneToMany(mappedBy = "turma", cascade = CascadeType.ALL)
    private List<UsuarioTurma> usuarios;

    @OneToMany(mappedBy = "turma", cascade = CascadeType.ALL)
    private List<ProfessorTurma> professores;

    @OneToMany(mappedBy = "turma", cascade = CascadeType.ALL)
    private List<Atividade> atividades;
}