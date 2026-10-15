package br.cefetmg.grow.dto;

import br.cefetmg.grow.model.UsuarioRecompensa;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class UsuarioRecompensaResponseDTO {

    private Long id;
    private LocalDate dataDesbloqueio;
    private Boolean equipada;
    private Long recompensaId;
    private String recompensaNome;
    private String recompensaRaridade;
    private Long usuarioId;
    private String usuarioNome;

    public UsuarioRecompensaResponseDTO(UsuarioRecompensa usuarioRecompensa) {
        this.id = usuarioRecompensa.getId();
        this.dataDesbloqueio = usuarioRecompensa.getDataDesbloqueio();
        this.equipada = usuarioRecompensa.getEquipada();
        
        if (usuarioRecompensa.getRecompensa() != null) {
            this.recompensaId = usuarioRecompensa.getRecompensa().getId();
            this.recompensaNome = usuarioRecompensa.getRecompensa().getNome();
            this.recompensaRaridade = usuarioRecompensa.getRecompensa().getRaridade();
        }
        
        if (usuarioRecompensa.getUsuario() != null) {
            this.usuarioId = usuarioRecompensa.getUsuario().getId();
            this.usuarioNome = usuarioRecompensa.getUsuario().getNome();
        }
    }
}