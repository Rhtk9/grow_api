package br.cefetmg.grow.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TurmaRequestDTO {

    @NotBlank(message = "Nome é obrigatório")
    private String nome;

    private String codigo;

    @NotBlank(message = "Descrição é obrigatória")
    private String descricao;

    private String imagem;

    @NotNull(message = "Ano é obrigatório")
    private Integer ano;

    @NotBlank(message = "Nível de ensino é obrigatório")
    private String nivelEnsino;

    @NotNull(message = "ID do usuário é obrigatório")
    private Long usuarioId;

    private Long disciplinaId;
}