package br.cefetmg.grow.dto;

import br.cefetmg.grow.model.UsuarioTarefa;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class UsuarioTarefaResponseDTO {

    private Long id;
    private BigDecimal nota;
    private LocalDate dataConclusao;
    private Boolean concluida;
    private String feedback;
    private Integer tempoGasto;
    private Integer tentativas;
    private Integer xpRecebido;
    private Long atividadeId;
    private String atividadeTitulo;
    private Long usuarioId;
    private String usuarioNome;

    public UsuarioTarefaResponseDTO(UsuarioTarefa usuarioTarefa) {
        this.id = usuarioTarefa.getId();
        this.nota = usuarioTarefa.getNota();
        this.dataConclusao = usuarioTarefa.getDataConclusao();
        this.concluida = usuarioTarefa.getConcluida();
        this.feedback = usuarioTarefa.getFeedback();
        this.tempoGasto = usuarioTarefa.getTempoGasto();
        this.tentativas = usuarioTarefa.getTentativas();
        this.xpRecebido = usuarioTarefa.getXpRecebido();
        
        if (usuarioTarefa.getAtividade() != null) {
            this.atividadeId = usuarioTarefa.getAtividade().getId();
            this.atividadeTitulo = usuarioTarefa.getAtividade().getTitulo();
        }
        
        if (usuarioTarefa.getUsuario() != null) {
            this.usuarioId = usuarioTarefa.getUsuario().getId();
            this.usuarioNome = usuarioTarefa.getUsuario().getNome();
        }
    }
}