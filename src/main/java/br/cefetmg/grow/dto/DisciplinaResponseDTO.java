package br.cefetmg.grow.dto;

import br.cefetmg.grow.model.Disciplina;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
public class DisciplinaResponseDTO {

    private Long id;
    private String nome;
    private String descricao;
    private String icone;
    private Integer corTema;
    private LocalTime horario;
    private Boolean ativa;
    private Integer quantidadeTurmas;

    public DisciplinaResponseDTO(Disciplina disciplina) {
        this.id = disciplina.getId();
        this.nome = disciplina.getNome();
        this.descricao = disciplina.getDescricao();
        this.icone = disciplina.getIcone();
        this.corTema = disciplina.getCorTema();
        this.horario = disciplina.getHorario();
        this.ativa = disciplina.getAtiva();
        
        if (disciplina.getTurmas() != null) {
            this.quantidadeTurmas = disciplina.getTurmas().size();
        }
    }
}