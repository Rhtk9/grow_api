package br.cefetmg.grow.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "TbNecessidadeFase")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class NecessidadeFase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CdNecessidade")
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "LuzIdeal", length = 20, nullable = false)
    private String luzIdeal;

    @Column(name = "RegasPorSemana", nullable = false)
    private Integer regasPorSemana;

    @Column(name = "AdubacoesPorMes", nullable = false)
    private Integer adubacoesPorMes;

    // Campos depreciados (agora aceitam NULL no banco)
    @Column(name = "AguaMedia")
    private Integer aguaMedia;

    @Column(name = "LuzMedia")
    private Integer luzMedia;

    @Column(name = "TemperaturaMedia")
    private Integer temperaturaMedia;

    @Column(name = "UmidadeMedia")
    private Integer umidadeMedia;
}