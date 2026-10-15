package br.cefetmg.grow.dto;

import br.cefetmg.grow.model.FasePlanta;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FasePlantaResponseDTO {

    @Schema(description = "ID da fase")
    private Long id;

    @Schema(description = "Nome da fase")
    private String nome;

    @Schema(description = "Ordem da fase")
    private Integer ordem;

    @Schema(description = "Dias base de duração")
    private Integer diasBase;

    @Schema(description = "XP necessário para evolução")
    private Integer xpNecessario;

    @Schema(description = "XP ganho")
    private Integer xpGanho;

    @Schema(description = "URL da imagem")
    private String imagem;

    @Schema(description = "ID da espécie")
    private Long especieId;

    @Schema(description = "Nome popular da espécie")
    private String especieNomePopular;

    @Schema(description = "ID da evolução da fase")
    private Long evolucaoId;

    @Schema(description = "Dias em estado ruim")
    private Integer diasRuim;

    @Schema(description = "Dias em estado médio")
    private Integer diasMedios;

    @Schema(description = "Dias em estado bom")
    private Integer diasBons;

    @Schema(description = "Bônus de evolução")
    private Integer bonus;

    @Schema(description = "Penalidade de evolução")
    private Integer penalidade;

    @Schema(description = "ID da necessidade da fase")
    private Long necessidadeId;

    @Schema(description = "Média de água necessária")
    private Integer aguaMedia;

    @Schema(description = "Média de luz necessária")
    private Integer luzMedia;

    @Schema(description = "Temperatura média ideal")
    private Integer temperaturaMedia;

    @Schema(description = "Umidade média ideal")
    private Integer umidadeMedia;

    @Schema(description = "Exposição solar ideal")
    private String luzIdeal;

    @Schema(description = "Frequência estimada de regas semanais")
    private Integer regasPorSemana;

    @Schema(description = "Frequência estimada de adubação mensal")
    private Integer adubacoesPorMes;

    public FasePlantaResponseDTO(FasePlanta fase) {
        if (fase == null) {
            return;
        }

        this.id = fase.getId();
        this.nome = fase.getNome();
        this.ordem = fase.getOrdem();
        this.diasBase = fase.getDiasBase();
        this.xpNecessario = fase.getXpNecessario();
        this.xpGanho = fase.getXpGanho();
        this.imagem = fase.getImagem();

        if (fase.getEspeciePlanta() != null) {
            this.especieId = fase.getEspeciePlanta().getId();
            this.especieNomePopular = fase.getEspeciePlanta().getNomePopular();
        }

        if (fase.getEvolucaoFase() != null) {
            this.evolucaoId = fase.getEvolucaoFase().getId();
            this.diasRuim = fase.getEvolucaoFase().getDiasRuim();
            this.diasMedios = fase.getEvolucaoFase().getDiasMedios();
            this.diasBons = fase.getEvolucaoFase().getDiasBons();
            this.bonus = fase.getEvolucaoFase().getBonus();
            this.penalidade = fase.getEvolucaoFase().getPenalidade();
        }

        if (fase.getNecessidadeFase() != null) {
            this.necessidadeId = fase.getNecessidadeFase().getId();
            this.aguaMedia = fase.getNecessidadeFase().getAguaMedia();
            this.luzMedia = fase.getNecessidadeFase().getLuzMedia();
            this.temperaturaMedia = fase.getNecessidadeFase().getTemperaturaMedia();
            this.umidadeMedia = fase.getNecessidadeFase().getUmidadeMedia();
        }

        if (fase.getNecessidadeFase() != null) {
            this.necessidadeId = fase.getNecessidadeFase().getId();
            this.luzIdeal = fase.getNecessidadeFase().getLuzIdeal();
            this.regasPorSemana = fase.getNecessidadeFase().getRegasPorSemana();
            this.adubacoesPorMes = fase.getNecessidadeFase().getAdubacoesPorMes();
            this.aguaMedia = fase.getNecessidadeFase().getAguaMedia();
            this.luzMedia = fase.getNecessidadeFase().getLuzMedia();
            this.temperaturaMedia = fase.getNecessidadeFase().getTemperaturaMedia();
            this.umidadeMedia = fase.getNecessidadeFase().getUmidadeMedia();
        }
    }
}