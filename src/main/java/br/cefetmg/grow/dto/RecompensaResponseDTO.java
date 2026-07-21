package br.cefetmg.grow.dto;

import br.cefetmg.grow.model.Recompensa;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RecompensaResponseDTO {

    private Long id;
    private String nome;
    private String descricao;
    private String tipo;
    private String raridade;
    private Integer xpNecessario;
    private String imagem;
    private Long quantidadeDesbloqueios;

    public RecompensaResponseDTO(Recompensa recompensa) {
        this.id = recompensa.getId();
        this.nome = recompensa.getNome();
        this.descricao = recompensa.getDescricao();
        this.tipo = recompensa.getTipo();
        this.raridade = recompensa.getRaridade();
        this.xpNecessario = recompensa.getXpNecessario();
        this.imagem = recompensa.getImagem();
        
        if (recompensa.getUsuarioRecompensas() != null) {
            this.quantidadeDesbloqueios = (long) recompensa.getUsuarioRecompensas().size();
        }
    }
}