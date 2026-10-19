package br.cefetmg.grow.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@lombok.Setter
@NoArgsConstructor
public class UsuarioTurmaRequestDTO {

    private Boolean ativo = true;
    private Boolean favorito = false;

    // preenchido automaticamente pelo token JWT
    private Long usuarioId;

    @NotNull(message = "ID da turma é obrigatório")
    private Long turmaId;
}
