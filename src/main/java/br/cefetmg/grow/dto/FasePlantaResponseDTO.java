package br.cefetmg.grow.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import br.cefetmg.grow.model.FasePlanta;

@Getter
@Setter
@NoArgsConstructor
public class FasePlantaResponseDTO {
    private Long id;
    private String nome;
    private Integer ordem;
    private Integer diasBase;
    private Integer xpNecessario;
    private Integer xpGanho;
    private String imagem;
    private Long especieId;
    private String especieNomePopular;
    private Long evolucaoId;
    private Integer diasRuim;
    private Integer diasMedios;
    private Integer diasBons;
    private Integer bonus;
    private Integer penalidade;
    private Long necessidadeId;
    private Integer aguaMedia;
    private Integer luzMedia;
    private Integer temperaturaMedia;
    private Integer umidadeMedia;

    public FasePlantaResponseDTO(FasePlanta fase) {
        this.id = fase.getId();
        this.nome = fase.getNome();
        this.ordem = fase.getOrdem();
        this.diasBase = fase.getDiasBase();
        this.xpNecessario = fase.getXpNecessario();
        this.xpGanho = fase.getXpGanho();

        // CORREÇÃO 1: Usando getImagem() que existe na sua entidade
        this.imagem = fase.getImagem();

        // CORREÇÃO 2: Usando getEspeciePlanta() para pegar o ID da espécie
        if (fase.getEspeciePlanta() != null) {
            this.especieId = fase.getEspeciePlanta().getId();
        }

        if (fase.getEvolucaoFase() != null) {
            this.diasRuim = fase.getEvolucaoFase().getDiasRuim();
            this.diasMedios = fase.getEvolucaoFase().getDiasMedios();
            this.diasBons = fase.getEvolucaoFase().getDiasBons();
            this.bonus = fase.getEvolucaoFase().getBonus();
            this.penalidade = fase.getEvolucaoFase().getPenalidade();
        }

        if (fase.getNecessidadeFase() != null) {
            this.aguaMedia = fase.getNecessidadeFase().getAguaMedia();
            this.luzMedia = fase.getNecessidadeFase().getLuzMedia();
            this.temperaturaMedia = fase.getNecessidadeFase().getTemperaturaMedia();
            this.umidadeMedia = fase.getNecessidadeFase().getUmidadeMedia();
        }
    }
}