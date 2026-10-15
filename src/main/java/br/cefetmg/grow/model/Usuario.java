package br.cefetmg.grow.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "tbUsuario")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CdUsuario")
    private Long id;

    @Column(name = "Nome", length = 100, nullable = false)
    private String nome;

    @Column(name = "email", length = 100, nullable = false, unique = true)
    private String email;

    @Column(name = "senha", length = 255, nullable = false)
    private String senha;

    @Column(name = "Img", length = 255)
    private String imagem;

    @Column(name = "Bio", length = 200)
    private String bio;

    @Column(name = "DtNasc", nullable = false)
    private LocalDate dataNascimento;

    @Column(name = "UtLogin")
    private LocalDateTime ultimoLogin;

    @Column(name = "Tema", nullable = false)
    private Integer tema;

    @Column(name = "idioma", nullable = false)
    private Integer idioma;

    @Column(name = "Notificacoes", nullable = false)
    private Boolean notificacoes;

    @Column(name = "Som", nullable = false)
    private Boolean som;

    @Column(name = "SeqDias", nullable = false)
    private Integer sequenciaDias;

    @Column(name = "PontT", nullable = false)
    private Integer pontuacaoTotal;

    @Column(name = "NivelUsuario", nullable = false)
    private Integer nivelUsuario;

    @Column(name = "XpT", nullable = false)
    private Integer xpTotal;

    @Column(name = "ativo")
    private Boolean ativo;

    @CreationTimestamp
    @Column(name = "DtCadastro", nullable = false, updatable = false)
    private LocalDateTime dataCadastro;
}