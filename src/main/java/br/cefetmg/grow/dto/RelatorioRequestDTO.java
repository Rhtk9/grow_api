package br.cefetmg.grow.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RelatorioRequestDTO {

    @NotNull(message = "Atividades cadastradas é obrigatório")
    @Positive(message = "Atividades cadastradas deve ser positivo")
    private Integer atividadesCadastradas;

    private Integer totalAtividades;

    @NotNull(message = "Média de notas é obrigatória")
    private Double mediaNotas;

    @NotNull(message = "Tempo de estudo é obrigatório")
    @Positive(message = "Tempo de estudo deve ser positivo")
    private Integer tempoEstudo;

    @NotNull(message = "Sequência de dias é obrigatória")
    @Positive(message = "Sequência de dias deve ser positiva")
    private Integer sequenciaDias;

    @NotNull(message = "Desempenho geral é obrigatório")
    private Integer desempenhoGeral;

    @NotNull(message = "XP total é obrigatório")
    @Positive(message = "XP total deve ser positivo")
    private Integer xpTotal;

    @NotNull(message = "Plantas desbloqueadas é obrigatório")
    @Positive(message = "Plantas desbloqueadas deve ser positivo")
    private Integer plantasDesbloqueadas;

    @NotNull(message = "ID do usuário é obrigatório")
    private Long usuarioId;
}