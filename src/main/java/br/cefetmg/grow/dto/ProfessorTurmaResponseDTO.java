package br.cefetmg.grow.dto;

import br.cefetmg.grow.model.ProfessorTurma;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class ProfessorTurmaResponseDTO {

    private Long id;
    private LocalDate dataVinculo;
    private Boolean excluir;
    private Boolean edicao;
    private Boolean gerenciarTarefas;
    private Long usuarioId;
    private String usuarioNome;
    private String usuarioEmail;
    private Long turmaId;
    private String turmaNome;
    private String turmaCodigo;
    private String disciplinaNome;

    public ProfessorTurmaResponseDTO(ProfessorTurma professorTurma) {
        this.id = professorTurma.getId();
        this.dataVinculo = professorTurma.getDataVinculo();
        this.excluir = professorTurma.getExcluir();
        this.edicao = professorTurma.getEdicao();
        this.gerenciarTarefas = professorTurma.getGerenciarTarefas();
        
        if (professorTurma.getUsuario() != null) {
            this.usuarioId = professorTurma.getUsuario().getId();
            this.usuarioNome = professorTurma.getUsuario().getNome();
            this.usuarioEmail = professorTurma.getUsuario().getEmail();
        }
        
        if (professorTurma.getTurma() != null) {
            this.turmaId = professorTurma.getTurma().getId();
            this.turmaNome = professorTurma.getTurma().getNome();
            this.turmaCodigo = professorTurma.getTurma().getCodigo();
            
            if (professorTurma.getTurma().getDisciplina() != null) {
                this.disciplinaNome = professorTurma.getTurma().getDisciplina().getNome();
            }
        }
    }
}