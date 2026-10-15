package br.cefetmg.grow.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RecompensaRequestDTO {

    @NotBlank(message = "Nome é obrigatório")
    private String nome;

    private String descricao;

    private String tipo;

    @NotBlank(message = "Raridade é obrigatória")
    private String raridade;

    @NotNull(message = "XP necessário é obrigatório")
    @Positive(message = "XP necessário deve ser positivo")
    private Integer xpNecessario;

    private String imagem;
}