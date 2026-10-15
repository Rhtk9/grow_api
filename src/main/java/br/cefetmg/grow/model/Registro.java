package br.cefetmg.grow.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "TbRegistro")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Registro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CdRegistro")
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "DtRegistro", nullable = false)
    private LocalDateTime dataRegistro;

    @Column(name = "TipoAcao", length = 20)
    private String tipoAcao;

    @Column(name = "ValorAnterior")
    private Integer valorAnterior;

    @Column(name = "ValorNovo")
    private Integer valorNovo;

    @Column(name = "XpRecebido", nullable = false)
    private Integer xpRecebido = 0;

    @Column(name = "Observacao", length = 200)
    private String observacao;

    // Campos depreciados (agora aceitam NULL no banco)
    @Column(name = "AguaPlanta")
    private Integer aguaPlanta;

    @Column(name = "Luz")
    private Integer luz;

    @Column(name = "Nutrientes")
    private Integer nutrientes;

    @Column(name = "QualidCuidado")
    private Integer qualidadeCuidado;

    @ManyToOne
    @JoinColumn(name = "TbPlantaUsuario_CdPlantaUsuario", nullable = false)
    private PlantaUsuario plantaUsuario;
}