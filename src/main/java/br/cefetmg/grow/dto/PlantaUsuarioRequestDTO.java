package br.cefetmg.grow.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PlantaUsuarioRequestDTO {

    @NotBlank(message = "O apelido é obrigatório")
    @Schema(description = "Apelido da planta", example = "Minha Jiboia")
    private String apelido;

    @NotNull(message = "O ID do usuário é obrigatório")
    @Schema(description = "ID do usuário", example = "1")
    private Long usuarioId;

    @NotNull(message = "O ID da espécie é obrigatório")
    @Schema(description = "ID da espécie da planta", example = "3")
    private Long especieId;

    @NotNull(message = "O ID da fase é obrigatório")
    @Schema(description = "ID da fase atual da planta", example = "1")
    private Long faseId;
}