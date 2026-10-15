package br.cefetmg.grow.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "TbUsuarioTurma")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioTurma {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CdUsuarioTurma")
    private Long id;

    @Column(name = "DtEntrada", nullable = false)
    private LocalDate dataEntrada;

    @Column(name = "Ativo")
    private Boolean ativo = true;

    @Column(name = "Favorito")
    private Boolean favorito = false;

    @ManyToOne
    @JoinColumn(name = "tbUsuario_CdUsuario", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "TbTurma_CdTurma", nullable = false)
    private Turma turma;
}