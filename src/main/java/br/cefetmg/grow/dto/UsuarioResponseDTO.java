// src/main/java/br/cefetmg/grow/dto/UsuarioResponseDTO.java

package br.cefetmg.grow.dto;

import br.cefetmg.grow.model.Usuario;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class UsuarioResponseDTO {
    private Long id;
    private String nome;
    private String email;
    private String senha; // 🔥 ADICIONADO - ESSENCIAL PARA VALIDAÇÃO
    private String imagem;
    private String bio;
    private LocalDate dataNascimento;
    private LocalDateTime ultimoLogin;
    private Integer sequenciaDias;
    private Integer pontuacaoTotal;
    private Integer nivelUsuario;
    private Integer xpTotal;
    private Boolean ativo;
    private LocalDateTime dataCadastro;

    public UsuarioResponseDTO(Usuario usuario) {
        this.id = usuario.getId();
        this.nome = usuario.getNome();
        this.email = usuario.getEmail();
        this.senha = usuario.getSenha(); // 🔥 LINHA ESSENCIAL
        this.imagem = usuario.getImagem();
        this.bio = usuario.getBio();
        this.dataNascimento = usuario.getDataNascimento();
        this.ultimoLogin = usuario.getUltimoLogin();
        this.sequenciaDias = usuario.getSequenciaDias();
        this.pontuacaoTotal = usuario.getPontuacaoTotal();
        this.nivelUsuario = usuario.getNivelUsuario();
        this.xpTotal = usuario.getXpTotal();
        this.ativo = usuario.getAtivo();
        this.dataCadastro = usuario.getDataCadastro();
    }
}