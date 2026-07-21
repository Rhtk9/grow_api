package br.cefetmg.grow.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "TbConteudo")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Conteudo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CdConteudo")
    private Long id;

    @Column(name = "Titulo", length = 100, nullable = false)
    private String titulo;

    @Column(name = "Tipo", length = 45, nullable = false)
    private String tipo;

    @Column(name = "URL", length = 500, nullable = false)
    private String url;

    @Column(name = "Nivel", nullable = false)
    private Integer nivel;

    @Column(name = "Tempo", nullable = false)
    private Integer tempo;

    @Column(name = "Publicado")
    private Boolean publicado = false;

    @Column(name = "DtCriacao", nullable = false)
    private LocalDateTime dataCriacao;

    @ManyToOne
    @JoinColumn(name = "TbDisciplina_CdDisciplina", nullable = false)
    private Disciplina disciplina;
}