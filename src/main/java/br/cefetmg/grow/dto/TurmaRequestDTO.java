package br.cefetmg.grow.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TurmaRequestDTO {

    @NotBlank(message = "Nome é obrigatório")
    private String nome;

    @NotBlank(message = "Código é obrigatório")
    private String codigo;

    @NotBlank(message = "Descrição é obrigatória")
    private String descricao;

    private String imagem;

    @NotNull(message = "Ano é obrigatório")
    private Integer ano;

    @NotBlank(message = "Nível de ensino é obrigatório")
    private String nivelEnsino;

    @NotNull(message = "ID da disciplina é obrigatório")
    private Long disciplinaId;
}