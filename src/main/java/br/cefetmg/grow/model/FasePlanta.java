package br.cefetmg.grow.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "TbFasePlanta")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class FasePlanta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CdFase")
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "Nome", length = 100, nullable = false)
    private String nome;

    @Column(name = "Ordem", nullable = false)
    private Integer ordem;

    @Column(name = "DiasBase", nullable = false)
    private Integer diasBase;

    @Column(name = "XpNecessario", nullable = false)
    private Integer xpNecessario;

    @Column(name = "XpGanho", nullable = false)
    private Integer xpGanho;

    @Column(name = "Img", length = 255, nullable = false)
    private String imagem;

    // Relacionamento com EspeciePlanta (ManyToOne)
    @ManyToOne
    @JoinColumn(name = "TbEspeciePlanta_CdEspecie", nullable = false)
    private EspeciePlanta especiePlanta;

    // Relacionamento com EvolucaoFase (OneToOne)
    @OneToOne
    @JoinColumn(name = "TbEvolucaofase_CdEvolucao", nullable = false)
    private EvolucaoFase evolucaoFase;

    // Relacionamento com NecessidadeFase (OneToOne)
    @OneToOne
    @JoinColumn(name = "TbNecessidadeFase_CdNecessidade", nullable = false)
    private NecessidadeFase necessidadeFase;
}