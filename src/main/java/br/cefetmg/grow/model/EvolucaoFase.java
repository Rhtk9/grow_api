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
@Table(name = "TbEvolucaofase")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class EvolucaoFase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CdEvolucao")
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "Bonus")
    private Integer bonus = 0;

    @Column(name = "Penalidade")
    private Integer penalidade = 0;

    // Campos depreciados (aceitam NULL no banco)
    @Column(name = "DiasRuim")
    private Integer diasRuim;

    @Column(name = "DiasMedios")
    private Integer diasMedios;

    @Column(name = "DiasBons")
    private Integer diasBons;
}