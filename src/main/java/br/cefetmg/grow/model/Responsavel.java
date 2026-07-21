package br.cefetmg.grow.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "TbResponsavel")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Responsavel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CdResponsavel")
    private Long id;

    @Column(name = "Parentesco", length = 50, nullable = false)
    private String parentesco;

    @Column(name = "DtVinculo", nullable = false)
    private LocalDate dataVinculo;

    @Column(name = "Notificacoes", nullable = false)
    private Boolean notificacoes;

    @ManyToOne
    @JoinColumn(name = "tbUsuario_CdUsuario", nullable = false)
    private Usuario usuario;
}