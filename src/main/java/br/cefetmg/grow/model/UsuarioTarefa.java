package br.cefetmg.grow.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "TbUsuarioTarefa")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioTarefa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CdTarefa")
    private Long id;

    @Column(name = "Nota", precision = 5, scale = 2, nullable = false)
    private BigDecimal nota;

    @Column(name = "DtConclusao", nullable = false)
    private LocalDate dataConclusao;

    @Column(name = "Concluida")
    private Boolean concluida = false;

    @Column(name = "Feedback", length = 200)
    private String feedback;

    @Column(name = "TempoGasto", nullable = false)
    private Integer tempoGasto;

    @Column(name = "Tentativas", nullable = false)
    private Integer tentativas;

    @Column(name = "XpRecebido", nullable = false)
    private Integer xpRecebido = 0;

    @ManyToOne
    @JoinColumn(name = "TbAtividade_CdAtividade", nullable = false)
    private Atividade atividade;

    @ManyToOne
    @JoinColumn(name = "tbUsuario_CdUsuario", nullable = false)
    private Usuario usuario;
}