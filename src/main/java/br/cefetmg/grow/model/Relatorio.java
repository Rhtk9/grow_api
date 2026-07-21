package br.cefetmg.grow.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "TbRelatorio")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Relatorio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CdRelatorio")
    private Long id;

    @Column(name = "AtividadesCadastras", nullable = false)
    private Integer atividadesCadastradas;

    @Column(name = "TotalAtiv")
    private Integer totalAtividades;

    @Column(name = "MedNotas", nullable = false)
    private Double mediaNotas;

    @Column(name = "TempoEstudo", nullable = false)
    private Integer tempoEstudo;

    @Column(name = "SeqDias", nullable = false)
    private Integer sequenciaDias;

    @Column(name = "DesempenhoGeral", nullable = false)
    private Integer desempenhoGeral;

    @Column(name = "XpTotal", nullable = false)
    private Integer xpTotal;

    @Column(name = "PlantasDesbloqueadas", nullable = false)
    private Integer plantasDesbloqueadas;

    @Column(name = "DtAtualizacao", nullable = false)
    private LocalDateTime dataAtualizacao;

    @ManyToOne
    @JoinColumn(name = "tbUsuario_CdUsuario", nullable = false)
    private Usuario usuario;
}