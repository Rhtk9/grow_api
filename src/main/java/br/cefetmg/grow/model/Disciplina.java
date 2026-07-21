package br.cefetmg.grow.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "TbDisciplina")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Disciplina {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CdDisciplina")
    private Long id;

    @Column(name = "Nome", length = 100, nullable = false)
    private String nome;

    @Column(name = "Descricao", length = 200, nullable = false)
    private String descricao;

    @Column(name = "Icone", length = 100)
    private String icone;

    @Column(name = "CorTema", nullable = false)
    private Integer corTema;

    @Column(name = "Horario")
    private LocalTime horario;

    @Column(name = "Ativa")
    private Boolean ativa = true;

    @OneToMany(mappedBy = "disciplina", cascade = CascadeType.ALL)
    private List<Turma> turmas;

    @OneToMany(mappedBy = "disciplina", cascade = CascadeType.ALL)
    private List<Conteudo> conteudos;
}