package br.cefetmg.grow.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
public class UsuarioTarefaRequestDTO {

    @NotNull(message = "Nota é obrigatória")
    @DecimalMin(value = "0.0", message = "Nota deve ser entre 0 e 10")
    @DecimalMax(value = "10.0", message = "Nota deve ser entre 0 e 10")
    private BigDecimal nota;

    private Boolean concluida = false;

    private String feedback;

    @NotNull(message = "Tempo gasto é obrigatório")
    @Positive(message = "Tempo gasto deve ser positivo")
    private Integer tempoGasto;

    @NotNull(message = "Tentativas é obrigatório")
    @Positive(message = "Tentativas deve ser positivo")
    private Integer tentativas;

    private Integer xpRecebido = 0;

    @NotNull(message = "ID da atividade é obrigatório")
    private Long atividadeId;

    @NotNull(message = "ID do usuário é obrigatório")
    private Long usuarioId;
}