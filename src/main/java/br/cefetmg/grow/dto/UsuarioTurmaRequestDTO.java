package br.cefetmg.grow.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UsuarioTurmaRequestDTO {

    private Boolean ativo = true;
    private Boolean favorito = false;

    @NotNull(message = "ID do usuário é obrigatório")
    private Long usuarioId;

    @NotNull(message = "ID da turma é obrigatório")
    private Long turmaId;
}
