package br.cefetmg.grow.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "TbPlantaUsuario")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlantaUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CdPlantaUsuario")
    private Long id;

    @Column(name = "Apelido", length = 45, nullable = false)
    private String apelido;

    @Column(name = "XpAtual", nullable = false)
    private Integer xpAtual = 0;

    @Column(name = "Nivel", nullable = false)
    private Integer nivel = 1;

    @Column(name = "Felicidade", nullable = false)
    private Integer felicidade = 100;

    @Column(name = "Saude", nullable = false)
    private Integer saude = 100;

    @ManyToOne
    @JoinColumn(name = "Fase", nullable = false)
    private FasePlanta fase;

    @Column(name = "DtCriacao", nullable = false)
    private LocalDate dataCriacao;

    @Column(name = "UltimaAlteracao", nullable = false)
    private LocalDate ultimaAlteracao;

    @ManyToOne
    @JoinColumn(name = "tbUsuario_CdUsuario", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "TbEspeciePlanta_CdEspecie", nullable = false)
    private EspeciePlanta especiePlanta;
}