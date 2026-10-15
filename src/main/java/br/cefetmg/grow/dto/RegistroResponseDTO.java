package br.cefetmg.grow.dto;

import java.time.LocalDateTime;

import br.cefetmg.grow.model.Registro;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RegistroResponseDTO {

    @Schema(description = "ID do registro")
    private Long id;

    @Schema(description = "Data e hora do registro")
    private LocalDateTime dataRegistro;

    @Schema(description = "Tipo de ação executada")
    private String tipoAcao;

    @Schema(description = "Valor anterior")
    private Integer valorAnterior;

    @Schema(description = "Valor novo")
    private Integer valorNovo;

    @Schema(description = "XP recebido com a ação")
    private Integer xpRecebido;

    @Schema(description = "Observações sobre a ação")
    private String observacao;

    @Schema(description = "ID da planta do usuário")
    private Long plantaUsuarioId;

    @Schema(description = "Apelido da planta do usuário")
    private String plantaApelido;

    // Campos depreciados
    private Integer aguaPlanta;
    private Integer luz;
    private Integer nutrientes;
    private Integer qualidadeCuidado;

    public RegistroResponseDTO(Registro registro) {
        if (registro == null) {
            return;
        }

        this.id = registro.getId();
        this.dataRegistro = registro.getDataRegistro();
        this.tipoAcao = registro.getTipoAcao();
        this.valorAnterior = registro.getValorAnterior();
        this.valorNovo = registro.getValorNovo();
        this.xpRecebido = registro.getXpRecebido();
        this.observacao = registro.getObservacao();

        this.aguaPlanta = registro.getAguaPlanta();
        this.luz = registro.getLuz();
        this.nutrientes = registro.getNutrientes();
        this.qualidadeCuidado = registro.getQualidadeCuidado();

        if (registro.getPlantaUsuario() != null) {
            this.plantaUsuarioId = registro.getPlantaUsuario().getId();
            this.plantaApelido = registro.getPlantaUsuario().getApelido();
        }
    }
}