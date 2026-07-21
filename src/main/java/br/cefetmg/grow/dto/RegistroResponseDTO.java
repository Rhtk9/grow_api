package br.cefetmg.grow.dto;

import br.cefetmg.grow.model.Registro;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class RegistroResponseDTO {

    private Long id;
    private LocalDate dataRegistro;
    private Integer aguaPlanta;
    private Integer luz;
    private Integer nutrientes;
    private Integer qualidadeCuidado;
    private Integer xpRecebido;
    private String observacao;
    private Long plantaUsuarioId;
    private String plantaApelido;

    public RegistroResponseDTO(Registro registro) {
        this.id = registro.getId();
        this.dataRegistro = registro.getDataRegistro();
        this.aguaPlanta = registro.getAguaPlanta();
        this.luz = registro.getLuz();
        this.nutrientes = registro.getNutrientes();
        this.qualidadeCuidado = registro.getQualidadeCuidado();
        this.xpRecebido = registro.getXpRecebido();
        this.observacao = registro.getObservacao();
        
        if (registro.getPlantaUsuario() != null) {
            this.plantaUsuarioId = registro.getPlantaUsuario().getId();
            this.plantaApelido = registro.getPlantaUsuario().getApelido();
        }
    }
}