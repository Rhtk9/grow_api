package br.cefetmg.grow.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProfessorTurmaRequestDTO {

    private Boolean excluir = false;
    private Boolean edicao = false;
    private Boolean gerenciarTarefas = false;

    @NotNull(message = "ID do usuário é obrigatório")
    private Long usuarioId;

    @NotNull(message = "ID da turma é obrigatório")
    private Long turmaId;
}