package br.cefetmg.grow.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import br.cefetmg.grow.model.PlantaUsuario;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @Schema(description = "Água atual da planta (0-100)")
    private Integer aguaAtual;

    @Schema(description = "Nutrientes atuais da planta (0-100)")
    private Integer nutrientesAtual;

    @Schema(description = "Luz acumulada atual da planta (0-100)")
    private Integer luzAtual;

    @Schema(description = "Nível de estresse acumulado da planta (0-100)")
    private Integer estresse;

    @Schema(description = "Posição atual da planta no ambiente (SOL_PLENO, MEIA_SOMBRA, SOMBRA)")
    private String posicaoAtual;

    @Schema(description = "Data e hora do último recálculo de estado")
    private LocalDateTime ultimaAtualizacao;

    @Schema(description = "Data e hora da última rega realizada")
    private LocalDateTime ultimaRega;

    @Schema(description = "Data e hora da última adubação realizada")
    private LocalDateTime ultimaAdubacao;

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

    @Schema(description = "URL da imagem da espécie")
    private String especieImagem;

    public PlantaUsuarioResponseDTO(PlantaUsuario planta) {
        if (planta == null) {
            return;
        }

        this.id = planta.getId();
        this.apelido = planta.getApelido();
        this.xpAtual = planta.getXpAtual();
        this.nivel = planta.getNivel();
        this.felicidade = planta.getFelicidade();
        this.saude = planta.getSaude();
        this.aguaAtual = planta.getAguaAtual();
        this.nutrientesAtual = planta.getNutrientesAtual();
        this.luzAtual = planta.getLuzAtual();
        this.estresse = planta.getEstresse();
        this.posicaoAtual = planta.getPosicaoAtual();
        this.ultimaAtualizacao = planta.getUltimaAtualizacao();
        this.ultimaRega = planta.getUltimaRega();
        this.ultimaAdubacao = planta.getUltimaAdubacao();
        this.dataCriacao = planta.getDataCriacao();
        this.ultimaAlteracao = planta.getUltimaAlteracao();

        if (planta.getFase() != null) {
            this.faseId = planta.getFase().getId();
            this.faseNome = planta.getFase().getNome();
        }

        if (planta.getUsuario() != null) {
            this.usuarioId = planta.getUsuario().getId();
            this.usuarioNome = planta.getUsuario().getNome();
        }

        if (planta.getEspeciePlanta() != null) {
            this.especieId = planta.getEspeciePlanta().getId();
            this.especieNomePopular = planta.getEspeciePlanta().getNomePopular();
            this.especieImagem = planta.getEspeciePlanta().getImagem();
        }
    }
}