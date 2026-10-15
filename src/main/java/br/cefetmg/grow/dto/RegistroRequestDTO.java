package br.cefetmg.grow.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RegistroRequestDTO {

    @NotNull(message = "ID da planta do usuário é obrigatório")
    @Schema(description = "ID da planta do usuário associada", example = "1")
    private Long plantaUsuarioId;

    @Schema(description = "Tipo de ação realizada (ex: REGA, ADUBACAO, PODA)", example = "REGA")
    @Size(max = 20)
    private String tipoAcao;

    @Schema(description = "Valor anterior do parâmetro alterado", example = "30")
    private Integer valorAnterior;

    @Schema(description = "Valor novo do parâmetro alterado", example = "80")
    private Integer valorNovo;

    @Schema(description = "XP ganho no registro", example = "15")
    private Integer xpRecebido = 0;

    @Size(max = 200, message = "Observação deve ter no máximo 200 caracteres")
    @Schema(description = "Observação do registro", example = "Rega realizada pela manhã")
    private String observacao;

    // Campos depreciados (opcionais)
    @Schema(description = "Nível de água aplicado (depreciado)", example = "50")
    private Integer aguaPlanta;

    @Schema(description = "Nível de luz fornecido (depreciado)", example = "70")
    private Integer luz;

    @Schema(description = "Nível de nutrientes aplicados (depreciado)", example = "40")
    private Integer nutrientes;

    @Schema(description = "Qualidade do cuidado (1 a 10)", example = "8")
    private Integer qualidadeCuidado;
}