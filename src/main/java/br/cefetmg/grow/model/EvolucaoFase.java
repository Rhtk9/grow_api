package br.cefetmg.grow.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "TbEvolucaofase")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EvolucaoFase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CdEvolucao")
    private Long id;

    @Column(name = "DiasRuim", nullable = false)
    private Integer diasRuim;

    @Column(name = "DiasMedios", nullable = false)
    private Integer diasMedios;

    @Column(name = "DiasBons", nullable = false)
    private Integer diasBons;

    @Column(name = "Bonus", nullable = false)
    private Integer bonus = 0;

    @Column(name = "Penalidade", nullable = false)
    private Integer penalidade = 0;
}