package br.cefetmg.grow.dto;

import br.cefetmg.grow.model.Conteudo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ConteudoResponseDTO {

    private Long id;
    private String titulo;
    private String tipo;
    private String url;
    private Integer nivel;
    private Integer tempo;
    private Boolean publicado;
    private LocalDateTime dataCriacao;
    private Long disciplinaId;
    private String disciplinaNome;

    public ConteudoResponseDTO(Conteudo conteudo) {
        this.id = conteudo.getId();
        this.titulo = conteudo.getTitulo();
        this.tipo = conteudo.getTipo();
        this.url = conteudo.getUrl();
        this.nivel = conteudo.getNivel();
        this.tempo = conteudo.getTempo();
        this.publicado = conteudo.getPublicado();
        this.dataCriacao = conteudo.getDataCriacao();
        
        if (conteudo.getDisciplina() != null) {
            this.disciplinaId = conteudo.getDisciplina().getId();
            this.disciplinaNome = conteudo.getDisciplina().getNome();
        }
    }
}