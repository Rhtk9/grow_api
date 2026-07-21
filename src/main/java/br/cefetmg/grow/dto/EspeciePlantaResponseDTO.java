package br.cefetmg.grow.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

import br.cefetmg.grow.model.EspeciePlanta;

@Getter
@Setter
@NoArgsConstructor
public class EspeciePlantaResponseDTO {

    @Schema(description = "ID da espécie", example = "1")
    private Long id;

    @Schema(description = "Nome popular", example = "Samambaia")
    private String nomePopular;

    @Schema(description = "Nome científico", example = "Nephrolepis exaltata")
    private String nomeCientifico;

    @Schema(description = "Classificação", example = "Pteridófita")
    private String classificacao;

    @Schema(description = "Descrição", example = "Planta de interior muito comum")
    private String descricao;

    @Schema(description = "Dificuldade de cultivo", example = "Fácil")
    private String dificuldadeCultivo;

    @Schema(description = "Ambiente", example = "Interior")
    private String ambiente;

    @Schema(description = "Temperatura ideal", example = "22.5")
    private Double temperatura;

    @Schema(description = "Umidade ideal", example = "60")
    private Integer umidade;

    @Schema(description = "Luminosidade ideal", example = "1000")
    private Integer luminosidade;

    @Schema(description = "URL da imagem", example = "https://exemplo.com/imagem.jpg")
    private String imagem;

    @Schema(description = "Raridade", example = "Comum")
    private String raridade;

    @Schema(description = "É pública?", example = "true")
    private Boolean publica;

    @Schema(description = "Data de criação", example = "2026-07-08")
    private LocalDate dataCriacao;

    @Schema(description = "ID do usuário criador", example = "1")
    private Long usuarioId;

    public EspeciePlantaResponseDTO(EspeciePlanta entity) {
        this.id = entity.getId();
        this.nomePopular = entity.getNomePopular();
        this.nomeCientifico = entity.getNomeCientifico();
        this.classificacao = entity.getClassificacao();
        this.descricao = entity.getDescricao();
        this.dificuldadeCultivo = entity.getDificuldadeCultivo();
        this.ambiente = entity.getAmbiente();
        this.temperatura = entity.getTemperatura();
        this.umidade = entity.getUmidade();
        this.luminosidade = entity.getLuminosidade();
        this.imagem = entity.getImagem();
        this.raridade = entity.getRaridade();
        this.publica = entity.getPublica();
        this.dataCriacao = entity.getDataCriacao();
        this.usuarioId = entity.getUsuario() != null ? entity.getUsuario().getId() : null;
    }
}