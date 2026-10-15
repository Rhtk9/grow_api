package br.cefetmg.grow.model;

import java.time.LocalDate;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "TbEspeciePlanta")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class EspeciePlanta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CdEspecie")
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "NomePopular", nullable = false, length = 100)
    private String nomePopular;

    @Column(name = "NomeCientifico", nullable = false, length = 100)
    private String nomeCientifico;

    @Column(name = "Classificacao", nullable = false, length = 100)
    private String classificacao;

    @Column(name = "Descricao", nullable = false, length = 500)
    private String descricao;

    @Column(name = "DificuldadeCultivo", nullable = false, length = 45)
    private String dificuldadeCultivo;

    @Column(name = "Ambiente", nullable = false, length = 45)
    private String ambiente;

    @Column(name = "Img", length = 255)
    private String imagem;

    @Column(name = "Publica", nullable = false)
    private Boolean publica = false;

    @CreationTimestamp
    @Column(name = "DtCriacao", nullable = false, updatable = false)
    private LocalDate dataCriacao;

    // Campos depreciados (aceitam NULL no banco)
    @Column(name = "Temperatura")
    private Double temperatura;

    @Column(name = "Umidade")
    private Integer umidade;

    @Column(name = "Luminosidade")
    private Integer luminosidade;

    @Column(name = "Raridade", length = 45)
    private String raridade;

    @ManyToOne
    @JoinColumn(name = "tbUsuario_CdUsuario", nullable = false)
    private Usuario usuario;

    @OneToMany(mappedBy = "especiePlanta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FasePlanta> fases;
}