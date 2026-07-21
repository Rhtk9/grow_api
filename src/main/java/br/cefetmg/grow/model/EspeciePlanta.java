package br.cefetmg.grow.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.util.List;

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

    @Column(name = "Temperatura", nullable = false)
    private Double temperatura;

    @Column(name = "Umidade", nullable = false)
    private Integer umidade;

    @Column(name = "Luminosidade", nullable = false)
    private Integer luminosidade;

    @Column(name = "Img", nullable = false, length = 255)
    private String imagem;

    @Column(name = "Raridade", nullable = false, length = 45)
    private String raridade;

    @Column(name = "Publica", nullable = false)
    private Boolean publica = false;

    @CreationTimestamp
    @Column(name = "DtCriacao", nullable = false, updatable = false)
    private LocalDate dataCriacao;

    // Relacionamento com Usuario (criador)
    @ManyToOne
    @JoinColumn(name = "tbUsuario_CdUsuario", nullable = false)
    private Usuario usuario;

    // Relacionamento com Fases (um para muitos)
    @OneToMany(mappedBy = "especiePlanta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FasePlanta> fases;

    // Futuramente: favoritos, avaliações, etc.
}