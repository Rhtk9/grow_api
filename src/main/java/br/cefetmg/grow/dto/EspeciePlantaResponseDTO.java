package br.cefetmg.grow.dto;

import java.time.LocalDate;

import br.cefetmg.grow.model.EspeciePlanta;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @Schema(description = "URL da imagem", example = "https://exemplo.com/imagem.jpg")
    private String imagem;

    @Schema(description = "É pública?", example = "true")
    private Boolean publica;

    @Schema(description = "Data de criação", example = "2026-07-08")
    private LocalDate dataCriacao;

    @Schema(description = "ID do usuário criador", example = "1")
    private Long usuarioId;

    @Schema(description = "Nome do usuário criador", example = "João Silva")
    private String nomeUsuario;

    // Campos depreciados
    @Schema(description = "Temperatura ideal (depreciado)")
    private Double temperatura;

    @Schema(description = "Umidade ideal (depreciado)")
    private Integer umidade;

    @Schema(description = "Luminosidade ideal (depreciado)")
    private Integer luminosidade;

    @Schema(description = "Raridade (depreciado)")
    private String raridade;

    public EspeciePlantaResponseDTO(EspeciePlanta entity) {
        if (entity == null) {
            return;
        }

        this.id = entity.getId();
        this.nomePopular = entity.getNomePopular();
        this.nomeCientifico = entity.getNomeCientifico();
        this.classificacao = entity.getClassificacao();
        this.descricao = entity.getDescricao();
        this.dificuldadeCultivo = entity.getDificuldadeCultivo();
        this.ambiente = entity.getAmbiente();
        this.imagem = entity.getImagem();
        this.publica = entity.getPublica();
        this.dataCriacao = entity.getDataCriacao();

        this.temperatura = entity.getTemperatura();
        this.umidade = entity.getUmidade();
        this.luminosidade = entity.getLuminosidade();
        this.raridade = entity.getRaridade();

        if (entity.getUsuario() != null) {
            this.usuarioId = entity.getUsuario().getId();
            this.nomeUsuario = entity.getUsuario().getNome();
        }
    }
}