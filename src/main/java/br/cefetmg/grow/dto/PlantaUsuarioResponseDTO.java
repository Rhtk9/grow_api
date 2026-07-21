package br.cefetmg.grow.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

import br.cefetmg.grow.model.PlantaUsuario;

@Getter
@Setter
@NoArgsConstructor
public class PlantaUsuarioResponseDTO {

    @Schema(description = "ID da planta do usuário")
    private Long id;

    @Schema(description = "Apelido da planta")
    private String apelido;

    @Schema(description = "XP atual da planta")
    private Integer xpAtual;

    @Schema(description = "Nível da planta")
    private Integer nivel;

    @Schema(description = "Felicidade da planta (0-100)")
    private Integer felicidade;

    @Schema(description = "Saúde da planta (0-100)")
    private Integer saude;

    @Schema(description = "ID da fase atual")
    private Long faseId;

    @Schema(description = "Nome da fase atual")
    private String faseNome;

    @Schema(description = "Data de criação")
    private LocalDate dataCriacao;

    @Schema(description = "Última alteração")
    private LocalDate ultimaAlteracao;

    @Schema(description = "ID do usuário")
    private Long usuarioId;

    @Schema(description = "Nome do usuário")
    private String usuarioNome;

    @Schema(description = "ID da espécie")
    private Long especieId;

    @Schema(description = "Nome popular da espécie")
    private String especieNomePopular;

    public PlantaUsuarioResponseDTO(PlantaUsuario planta) {
        this.id = planta.getId();
        this.apelido = planta.getApelido();
        this.xpAtual = planta.getXpAtual();
        this.nivel = planta.getNivel();
        this.felicidade = planta.getFelicidade();
        this.saude = planta.getSaude();
        this.faseId = planta.getFase().getId();
        this.faseNome = planta.getFase().getNome();
        this.dataCriacao = planta.getDataCriacao();
        this.ultimaAlteracao = planta.getUltimaAlteracao();
        this.usuarioId = planta.getUsuario().getId();
        this.usuarioNome = planta.getUsuario().getNome();
        this.especieId = planta.getEspeciePlanta().getId();
        this.especieNomePopular = planta.getEspeciePlanta().getNomePopular();
    }
}