package br.cefetmg.grow.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AvaliacaoRequestDTO {

    @NotNull(message = "A nota é obrigatória.")
    @Min(value = 1, message = "A nota mínima é 1.")
    @Max(value = 5, message = "A nota máxima é 5.")
    private Integer nota;

    @NotBlank(message = "O comentário é obrigatório.")
    @Size(max = 1000, message = "O comentário pode ter no máximo 1000 caracteres.")
    private String comentario;

    @NotNull(message = "O ID da espécie é obrigatório.")
    private Long especieId;

    @NotNull(message = "O ID do usuário é obrigatório.")
    private Long usuarioId;
}