package br.cefetmg.grow.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PegarAtividadeRequestDTO {

    @NotNull(message = "ID da atividade é obrigatório")
    private Long atividadeId;
}
