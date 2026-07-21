package br.cefetmg.grow.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "TbUsuarioRecompensa")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioRecompensa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CdUsuarioRecompensa")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "TbRecompensa_CdRecompensa", nullable = false)
    private Recompensa recompensa;

    @ManyToOne
    @JoinColumn(name = "tbUsuario_CdUsuario", nullable = false)
    private Usuario usuario;

    @Column(name = "DtDesbloqueio")
    private LocalDate dataDesbloqueio;

    @Column(name = "Equipada", nullable = false)
    private Boolean equipada = false;
}
