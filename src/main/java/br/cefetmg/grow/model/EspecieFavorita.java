package br.cefetmg.grow.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "TbEspeciefavorita")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EspecieFavorita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CdFavorita")
    private Long id;

    @Column(name = "DtFavorito", nullable = false)
    private LocalDate dataFavorito;

    @ManyToOne
    @JoinColumn(name = "tbUsuario_CdUsuario", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "TbEspeciePlanta_CdEspecie", nullable = false)
    private EspeciePlanta especiePlanta;
}