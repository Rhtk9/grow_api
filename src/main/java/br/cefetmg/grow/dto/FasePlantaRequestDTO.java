package br.cefetmg.grow.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FasePlantaRequestDTO {

    @NotBlank(message = "Nome da fase é obrigatório")
    @Size(max = 100)
    private String nome;

    @NotNull(message = "Ordem é obrigatória")
    @Positive(message = "Ordem deve ser positiva")
    private Integer ordem;

    @NotNull(message = "Dias base são obrigatórios")
    @Positive(message = "Dias base deve ser positivo")
    private Integer diasBase;

    @NotNull(message = "XP necessário é obrigatório")
    @PositiveOrZero(message = "XP necessário deve ser >= 0")
    private Integer xpNecessario;

    @NotNull(message = "XP ganho é obrigatório")
    @PositiveOrZero(message = "XP ganho deve ser >= 0")
    private Integer xpGanho;

    @NotBlank(message = "URL da imagem é obrigatória")
    private String imagem;

    @NotNull(message = "ID da espécie é obrigatório")
    private Long especieId;

    // Dados da evolução (criaremos as entidades junto com a fase)
    @NotNull(message = "DiasRuim é obrigatório")
    @PositiveOrZero
    private Integer diasRuim;

    @NotNull(message = "DiasMedios é obrigatório")
    @PositiveOrZero
    private Integer diasMedios;

    @NotNull(message = "DiasBons é obrigatório")
    @PositiveOrZero
    private Integer diasBons;

    private Integer bonus = 0;
    private Integer penalidade = 0;

    // Dados das necessidades
    @NotNull(message = "AguaMedia é obrigatória")
    @PositiveOrZero
    private Integer aguaMedia;

    @NotNull(message = "LuzMedia é obrigatória")
    @PositiveOrZero
    private Integer luzMedia;

    @NotNull(message = "TemperaturaMedia é obrigatória")
    @PositiveOrZero
    private Integer temperaturaMedia;

    @NotNull(message = "UmidadeMedia é obrigatória")
    @PositiveOrZero
    private Integer umidadeMedia;
}