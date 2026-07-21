package br.cefetmg.grow.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Future;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class AtividadeRequestDTO {

    @NotBlank(message = "Título é obrigatório")
    private String titulo;

    @NotBlank(message = "Descrição é obrigatória")
    private String descricao;

    @NotNull(message = "Pontuação é obrigatória")
    @Positive(message = "Pontuação deve ser positiva")
    private Integer pontuacao;

    @NotNull(message = "XP é obrigatório")
    @Positive(message = "XP deve ser positivo")
    private Integer xp;

    @NotNull(message = "Data de entrega é obrigatória")
    @Future(message = "Data de entrega deve ser no futuro")
    private LocalDate dataEntrega;

    @NotNull(message = "Tentativas é obrigatório")
    @Positive(message = "Tentativas deve ser positivo")
    private Integer tentativas;

    @NotBlank(message = "Status é obrigatório")
    private String status;

    @NotNull(message = "Dificuldade é obrigatória")
    private Integer dificuldade;

    @NotNull(message = "ID da turma é obrigatório")
    private Long turmaId;
}