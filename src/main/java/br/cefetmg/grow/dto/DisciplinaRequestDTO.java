package br.cefetmg.grow.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Getter
@NoArgsConstructor
public class DisciplinaRequestDTO {

    @NotBlank(message = "Nome é obrigatório")
    private String nome;

    @NotBlank(message = "Descrição é obrigatória")
    private String descricao;

    private String icone;

    @NotNull(message = "Cor do tema é obrigatória")
    private Integer corTema;

    private LocalTime horario;

    private Boolean ativa = true;
}