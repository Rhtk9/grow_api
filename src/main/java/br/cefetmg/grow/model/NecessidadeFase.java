package br.cefetmg.grow.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "TbNecessidadeFase")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NecessidadeFase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CdNecessidade")
    private Long id;

    @Column(name = "AguaMedia", nullable = false)
    private Integer aguaMedia;

    @Column(name = "LuzMedia", nullable = false)
    private Integer luzMedia;

    @Column(name = "TemperaturaMedia", nullable = false)
    private Integer temperaturaMedia;

    @Column(name = "UmidadeMedia", nullable = false)
    private Integer umidadeMedia;
}