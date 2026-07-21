package br.cefetmg.grow.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ResponsavelRequestDTO {

    @NotBlank(message = "Parentesco é obrigatório")
    private String parentesco;

    @NotNull(message = "Notificações é obrigatório")
    private Boolean notificacoes;

    @NotNull(message = "ID do usuário é obrigatório")
    private Long usuarioId;
}