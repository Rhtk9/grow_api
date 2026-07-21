package br.cefetmg.grow.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UsuarioRecompensaRequestDTO {

    private Boolean equipada = false;

    @NotNull(message = "ID da recompensa é obrigatório")
    private Long recompensaId;

    @NotNull(message = "ID do usuário é obrigatório")
    private Long usuarioId;
}