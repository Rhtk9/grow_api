package br.cefetmg.grow.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ConteudoRequestDTO {

    @NotBlank(message = "Título é obrigatório")
    private String titulo;

    @NotBlank(message = "Tipo é obrigatório")
    private String tipo;

    @NotBlank(message = "URL é obrigatória")
    private String url;

    @NotNull(message = "Nível é obrigatório")
    @Positive(message = "Nível deve ser positivo")
    private Integer nivel;

    @NotNull(message = "Tempo é obrigatório")
    @Positive(message = "Tempo deve ser positivo")
    private Integer tempo;

    private Boolean publicado = false;

    @NotNull(message = "ID da disciplina é obrigatório")
    private Long disciplinaId;
}