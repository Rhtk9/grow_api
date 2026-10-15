package br.cefetmg.grow.dto;

import br.cefetmg.grow.model.Relatorio;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class RelatorioResponseDTO {

    private Long id;
    private Integer atividadesCadastradas;
    private Integer totalAtividades;
    private Double mediaNotas;
    private Integer tempoEstudo;
    private Integer sequenciaDias;
    private Integer desempenhoGeral;
    private Integer xpTotal;
    private Integer plantasDesbloqueadas;
    private LocalDateTime dataAtualizacao;
    private Long usuarioId;
    private String usuarioNome;

    public RelatorioResponseDTO(Relatorio relatorio) {
        this.id = relatorio.getId();
        this.atividadesCadastradas = relatorio.getAtividadesCadastradas();
        this.totalAtividades = relatorio.getTotalAtividades();
        this.mediaNotas = relatorio.getMediaNotas();
        this.tempoEstudo = relatorio.getTempoEstudo();
        this.sequenciaDias = relatorio.getSequenciaDias();
        this.desempenhoGeral = relatorio.getDesempenhoGeral();
        this.xpTotal = relatorio.getXpTotal();
        this.plantasDesbloqueadas = relatorio.getPlantasDesbloqueadas();
        this.dataAtualizacao = relatorio.getDataAtualizacao();
        
        if (relatorio.getUsuario() != null) {
            this.usuarioId = relatorio.getUsuario().getId();
            this.usuarioNome = relatorio.getUsuario().getNome();
        }
    }
}