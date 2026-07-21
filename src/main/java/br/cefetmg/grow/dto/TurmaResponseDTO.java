package br.cefetmg.grow.dto;

import br.cefetmg.grow.model.Turma;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class TurmaResponseDTO {

    private Long id;
    private String nome;
    private String codigo;
    private String descricao;
    private String imagem;
    private Integer ano;
    private String nivelEnsino;
    private LocalDate dataCriacao;
    private Long disciplinaId;
    private String disciplinaNome;
    private Integer quantidadeAlunos;
    private Integer quantidadeProfessores;
    private Integer quantidadeAtividades;

    public TurmaResponseDTO(Turma turma) {
        this.id = turma.getId();
        this.nome = turma.getNome();
        this.codigo = turma.getCodigo();
        this.descricao = turma.getDescricao();
        this.imagem = turma.getImagem();
        this.ano = turma.getAno();
        this.nivelEnsino = turma.getNivelEnsino();
        this.dataCriacao = turma.getDataCriacao();
        
        if (turma.getDisciplina() != null) {
            this.disciplinaId = turma.getDisciplina().getId();
            this.disciplinaNome = turma.getDisciplina().getNome();
        }
        
        if (turma.getUsuarios() != null) {
            this.quantidadeAlunos = turma.getUsuarios().size();
        }
        
        if (turma.getProfessores() != null) {
            this.quantidadeProfessores = turma.getProfessores().size();
        }
        
        if (turma.getAtividades() != null) {
            this.quantidadeAtividades = turma.getAtividades().size();
        }
    }
}