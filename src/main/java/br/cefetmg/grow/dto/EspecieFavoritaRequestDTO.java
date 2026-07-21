package br.cefetmg.grow.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class EspecieFavoritaRequestDTO {

    @NotNull(message = "O ID do usuário é obrigatório")
    @Schema(description = "ID do usuário", example = "1")
    private Long usuarioId;

    @NotNull(message = "O ID da espécie é obrigatório")
    @Schema(description = "ID da espécie da planta", example = "5")
    private Long especieId;
}