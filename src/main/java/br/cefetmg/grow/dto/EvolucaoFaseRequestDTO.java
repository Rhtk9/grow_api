package br.cefetmg.grow.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EvolucaoFaseRequestDTO {

    @Schema(description = "Bônus de XP concedido por ótimos cuidados", example = "5")
    private Integer bonus = 0;

    @Schema(description = "Penalidade de XP aplicada por maus cuidados", example = "3")
    private Integer penalidade = 0;

    // Campos depreciados (opcionais)
    @Schema(description = "Dias em estado ruim (depreciado)", example = "2")
    private Integer diasRuim;

    @Schema(description = "Dias em estado médio (depreciado)", example = "5")
    private Integer diasMedios;

    @Schema(description = "Dias em estado bom (depreciado)", example = "10")
    private Integer diasBons;
}
