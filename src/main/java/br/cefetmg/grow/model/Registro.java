package br.cefetmg.grow.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "TbRegistro")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Registro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CdRegistro")
    private Long id;

    @Column(name = "DtRegistro", nullable = false)
    private LocalDate dataRegistro;

    @Column(name = "AguaPlanta", nullable = false)
    private Integer aguaPlanta;

    @Column(name = "Luz", nullable = false)
    private Integer luz;

    @Column(name = "Nutrientes", nullable = false)
    private Integer nutrientes;

    @Column(name = "QualidCuidado", nullable = false)
    private Integer qualidadeCuidado;

    @Column(name = "XpRecebido", nullable = false)
    private Integer xpRecebido = 0;

    @Column(name = "Observacao", length = 200)
    private String observacao;

    @ManyToOne
    @JoinColumn(name = "TbPlantaUsuario_CdPlantaUsuario", nullable = false)
    private PlantaUsuario plantaUsuario;
}