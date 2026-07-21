package br.cefetmg.grow.dto;

import br.cefetmg.grow.model.UsuarioTurma;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class UsuarioTurmaResponseDTO {

    private Long id;
    private LocalDate dataEntrada;
    private Boolean ativo;
    private Boolean favorito;
    private Long usuarioId;
    private String usuarioNome;
    private String usuarioEmail;
    private Long turmaId;
    private String turmaNome;
    private String turmaCodigo;
    private String disciplinaNome;

    public UsuarioTurmaResponseDTO(UsuarioTurma usuarioTurma) {
        this.id = usuarioTurma.getId();
        this.dataEntrada = usuarioTurma.getDataEntrada();
        this.ativo = usuarioTurma.getAtivo();
        this.favorito = usuarioTurma.getFavorito();
        
        if (usuarioTurma.getUsuario() != null) {
            this.usuarioId = usuarioTurma.getUsuario().getId();
            this.usuarioNome = usuarioTurma.getUsuario().getNome();
            this.usuarioEmail = usuarioTurma.getUsuario().getEmail();
        }
        
        if (usuarioTurma.getTurma() != null) {
            this.turmaId = usuarioTurma.getTurma().getId();
            this.turmaNome = usuarioTurma.getTurma().getNome();
            this.turmaCodigo = usuarioTurma.getTurma().getCodigo();
            
            if (usuarioTurma.getTurma().getDisciplina() != null) {
                this.disciplinaNome = usuarioTurma.getTurma().getDisciplina().getNome();
            }
        }
    }
}