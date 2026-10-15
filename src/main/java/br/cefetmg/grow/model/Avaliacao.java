package br.cefetmg.grow.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "TbAvaliacao")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Avaliacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CdAvaliacao")
    private Long id;

    @Column(name = "Nota", nullable = false)
    private Integer nota;

    @Column(name = "Comentario", length = 1000, nullable = false)
    private String comentario;

    @Column(name = "DtAvaliacao", nullable = false)
    private LocalDateTime dataAvaliacao;

    @ManyToOne
    @JoinColumn(name = "TbEspeciePlanta_CdEspecie", nullable = false)
    private EspeciePlanta especie;

    @ManyToOne
    @JoinColumn(name = "tbUsuario_CdUsuario", nullable = false)
    private Usuario usuario;
}