package br.cefetmg.grow.dto;

import br.cefetmg.grow.model.Atividade;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class AtividadeResponseDTO {

    private Long id;
    private String titulo;
    private String descricao;
    private Integer pontuacao;
    private Integer xp;
    private LocalDate dataCriacao;
    private LocalDate dataEntrega;
    private Integer tentativas;
    private String status;
    private Integer dificuldade;
    private Long turmaId;
    private String turmaNome;
    private Integer totalAlunos;
    private Integer alunosConcluidos;

    public AtividadeResponseDTO(Atividade atividade) {
        this.id = atividade.getId();
        this.titulo = atividade.getTitulo();
        this.descricao = atividade.getDescricao();
        this.pontuacao = atividade.getPontuacao();
        this.xp = atividade.getXp();
        this.dataCriacao = atividade.getDataCriacao();
        this.dataEntrega = atividade.getDataEntrega();
        this.tentativas = atividade.getTentativas();
        this.status = atividade.getStatus();
        this.dificuldade = atividade.getDificuldade();
        
        if (atividade.getTurma() != null) {
            this.turmaId = atividade.getTurma().getId();
            this.turmaNome = atividade.getTurma().getNome();
            
            if (atividade.getTurma().getUsuarios() != null) {
                this.totalAlunos = atividade.getTurma().getUsuarios().size();
            }
        }
        
        if (atividade.getUsuarioTarefas() != null) {
            this.alunosConcluidos = (int) atividade.getUsuarioTarefas().stream()
                    .filter(ut -> ut.getConcluida() != null && ut.getConcluida())
                    .count();
        }
    }
}