package br.cefetmg.grow.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "TbRecompensa")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Recompensa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CdRecompensa")
    private Long id;

    @Column(name = "Nome", length = 100, nullable = false)
    private String nome;

    @Column(name = "Descricao", length = 200)
    private String descricao;

    @Column(name = "Tipo", length = 45)
    private String tipo;

    @Column(name = "Raridade", length = 45, nullable = false)
    private String raridade;

    @Column(name = "XpNecessario", nullable = false)
    private Integer xpNecessario;

    @Column(name = "Img", length = 255)
    private String imagem;

    @OneToMany(mappedBy = "recompensa", cascade = CascadeType.ALL)
    private List<UsuarioRecompensa> usuarioRecompensas;
}