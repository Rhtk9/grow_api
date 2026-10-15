package br.cefetmg.grow.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FasePlantaRequestDTO {

    @NotBlank(message = "Nome da fase é obrigatório")
    @Size(max = 100)
    @Schema(description = "Nome da fase", example = "Muda")
    private String nome;

    @NotNull(message = "Ordem é obrigatória")
    @Positive(message = "Ordem deve ser positiva")
    @Schema(description = "Ordem sequencial da fase", example = "1")
    private Integer ordem;

    @NotNull(message = "Dias base são obrigatórios")
    @Positive(message = "Dias base deve ser positivo")
    @Schema(description = "Dias base de duração da fase", example = "15")
    private Integer diasBase;

    @NotNull(message = "XP necessário é obrigatório")
    @PositiveOrZero(message = "XP necessário deve ser >= 0")
    @Schema(description = "XP necessário para avançar de fase", example = "100")
    private Integer xpNecessario;

    @Schema(description = "XP ganho (campo depreciado)", example = "0")
    private Integer xpGanho;

    @NotBlank(message = "URL da imagem é obrigatória")
    @Schema(description = "URL da imagem da fase", example = "https://exemplo.com/muda.png")
    private String imagem;

    @NotNull(message = "ID da espécie é obrigatório")
    @Schema(description = "ID da espécie associada", example = "1")
    private Long especieId;

    // Dados da evolução
    @NotNull(message = "DiasRuim é obrigatório")
    @PositiveOrZero
    @Schema(description = "Dias em estado ruim", example = "3")
    private Integer diasRuim;

    @NotNull(message = "DiasMedios é obrigatório")
    @PositiveOrZero
    @Schema(description = "Dias em estado médio", example = "5")
    private Integer diasMedios;

    @NotNull(message = "DiasBons é obrigatório")
    @PositiveOrZero
    @Schema(description = "Dias em estado bom", example = "7")
    private Integer diasBons;

    @Schema(description = "Bônus de XP/Evolução", example = "10")
    private Integer bonus = 0;

    @Schema(description = "Penalidade de XP/Evolução", example = "5")
    private Integer penalidade = 0;

    // Dados das necessidades
    @NotNull(message = "AguaMedia é obrigatória")
    @PositiveOrZero
    @Schema(description = "Necessidade média de água (0-100)", example = "60")
    private Integer aguaMedia;

    @NotNull(message = "LuzMedia é obrigatória")
    @PositiveOrZero
    @Schema(description = "Necessidade média de luz (0-100)", example = "70")
    private Integer luzMedia;

    @NotNull(message = "TemperaturaMedia é obrigatória")
    @PositiveOrZero
    @Schema(description = "Temperatura ideal (°C)", example = "25")
    private Integer temperaturaMedia;

    @NotNull(message = "UmidadeMedia é obrigatória")
    @PositiveOrZero
    @Schema(description = "Umidade ideal (%)", example = "50")
    private Integer umidadeMedia;

    @NotBlank(message = "Luz ideal é obrigatória")
    @Schema(description = "Exposição solar ideal (ex: SOL_PLENO, MEIA_SOMBRA, SOMBRA)", example = "MEIA_SOMBRA")
    private String luzIdeal;

    @NotNull(message = "Regas por semana é obrigatório")
    @PositiveOrZero
    @Schema(description = "Frequência estimada de regas semanais", example = "3")
    private Integer regasPorSemana;

    @NotNull(message = "Adubações por mês é obrigatório")
    @PositiveOrZero
    @Schema(description = "Frequência estimada de adubação mensal", example = "1")
    private Integer adubacoesPorMes;
}