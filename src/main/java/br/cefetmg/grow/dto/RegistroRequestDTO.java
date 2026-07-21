package br.cefetmg.grow.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RegistroRequestDTO {

    @NotNull(message = "Água da planta é obrigatório")
    private Integer aguaPlanta;

    @NotNull(message = "Luz é obrigatória")
    private Integer luz;

    @NotNull(message = "Nutrientes é obrigatório")
    private Integer nutrientes;

    @NotNull(message = "Qualidade do cuidado é obrigatória")
    private Integer qualidadeCuidado;

    private Integer xpRecebido = 0;

    @Size(max = 200, message = "Observação deve ter no máximo 200 caracteres")
    private String observacao;

    @NotNull(message = "ID da planta do usuário é obrigatório")
    private Long plantaUsuarioId;
}