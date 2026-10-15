package br.cefetmg.grow.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    // Campo depreciado (agora aceita NULL no banco)
    @Column(name = "XpGanho")
    private Integer xpGanho;

    @Column(name = "Img", length = 255, nullable = false)
    private String imagem;

    @ManyToOne
    @JoinColumn(name = "TbEspeciePlanta_CdEspecie", nullable = false)
    private EspeciePlanta especiePlanta;

    @OneToOne
    @JoinColumn(name = "TbEvolucaofase_CdEvolucao", nullable = false, unique = true)
    private EvolucaoFase evolucaoFase;

    @OneToOne
    @JoinColumn(name = "TbNecessidadeFase_CdNecessidade", nullable = false, unique = true)
    private NecessidadeFase necessidadeFase;
}