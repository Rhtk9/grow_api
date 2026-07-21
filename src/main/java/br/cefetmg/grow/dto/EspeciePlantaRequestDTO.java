package br.cefetmg.grow.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class EspeciePlantaRequestDTO {

    @NotBlank(message = "Nome popular é obrigatório")
    @Size(max = 100)
    @Schema(description = "Nome popular da planta", example = "Samambaia")
    private String nomePopular;

    @NotBlank(message = "Nome científico é obrigatório")
    @Size(max = 100)
    @Schema(description = "Nome científico da planta", example = "Nephrolepis exaltata")
    private String nomeCientifico;

    @NotBlank(message = "Classificação é obrigatória")
    @Size(max = 100)
    @Schema(description = "Classificação (ex: Pteridófita, Angiosperma)", example = "Pteridófita")
    private String classificacao;

    @NotBlank(message = "Descrição é obrigatória")
    @Size(max = 500)
    @Schema(description = "Descrição da planta", example = "Planta de interior muito comum, fácil de cuidar.")
    private String descricao;

    @NotBlank(message = "Dificuldade de cultivo é obrigatória")
    @Size(max = 45)
    @Schema(description = "Dificuldade: Fácil, Médio, Difícil", example = "Fácil")
    private String dificuldadeCultivo;

    @NotBlank(message = "Ambiente é obrigatório")
    @Size(max = 45)
    @Schema(description = "Ambiente: Interior, Exterior, Meia-sombra", example = "Interior")
    private String ambiente;

    @NotNull(message = "Temperatura é obrigatória")
    @Positive
    @Schema(description = "Temperatura ideal em graus Celsius", example = "22.5")
    private Double temperatura;

    @NotNull(message = "Umidade é obrigatória")
    @Positive
    @Schema(description = "Umidade ideal em porcentagem", example = "60")
    private Integer umidade;

    @NotNull(message = "Luminosidade é obrigatória")
    @Positive
    @Schema(description = "Luminosidade ideal (lux)", example = "1000")
    private Integer luminosidade;

    @NotBlank(message = "URL da imagem é obrigatória")
    @Size(max = 255)
    @Schema(description = "URL da imagem de capa", example = "https://exemplo.com/imagem.jpg")
    private String imagem;

    @NotBlank(message = "Raridade é obrigatória")
    @Size(max = 45)
    @Schema(description = "Raridade: Comum, Rara, Muito Rara", example = "Comum")
    private String raridade;

    private Boolean publica = false;

    @NotNull(message = "ID do usuário criador é obrigatório")
    @Schema(description = "ID do usuário que está cadastrando a espécie", example = "1")
    private Long usuarioId;
}