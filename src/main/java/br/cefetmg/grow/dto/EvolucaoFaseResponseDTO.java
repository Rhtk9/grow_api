package br.cefetmg.grow.dto;

import br.cefetmg.grow.model.EvolucaoFase;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EvolucaoFaseResponseDTO {

    @Schema(description = "ID da regra de evolução de fase", example = "1")
    private Long id;

    @Schema(description = "Bônus de XP concedido", example = "5")
    private Integer bonus;

    @Schema(description = "Penalidade de XP aplicada", example = "3")
    private Integer penalidade;

    // Campos depreciados
    @Schema(description = "Dias em estado ruim (depreciado)")
    private Integer diasRuim;

    @Schema(description = "Dias em estado médio (depreciado)")
    private Integer diasMedios;

    @Schema(description = "Dias em estado bom (depreciado)")
    private Integer diasBons;

    public EvolucaoFaseResponseDTO(EvolucaoFase entity) {
        if (entity == null) {
            return;
        }

        this.id = entity.getId();
        this.bonus = entity.getBonus();
        this.penalidade = entity.getPenalidade();
        this.diasRuim = entity.getDiasRuim();
        this.diasMedios = entity.getDiasMedios();
        this.diasBons = entity.getDiasBons();
    }
}