package br.cefetmg.grow.dto;

import br.cefetmg.grow.model.Responsavel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class ResponsavelResponseDTO {

    private Long id;
    private String parentesco;
    private LocalDate dataVinculo;
    private Boolean notificacoes;

    private Long usuarioId; // ALUNO
    private String usuarioNome;
    private String usuarioEmail;

    private Long responsavelId; // NOVO
    private String responsavelNome;

    public ResponsavelResponseDTO(Responsavel responsavel) {
        this.id = responsavel.getId();
        this.parentesco = responsavel.getParentesco();
        this.dataVinculo = responsavel.getDataVinculo();
        this.notificacoes = responsavel.getNotificacoes();

        if (responsavel.getUsuario() != null) {
            this.usuarioId = responsavel.getUsuario().getId();
            this.usuarioNome = responsavel.getUsuario().getNome();
            this.usuarioEmail = responsavel.getUsuario().getEmail();
        }

        if (responsavel.getResponsavel() != null) {
            this.responsavelId = responsavel.getResponsavel().getId();
            this.responsavelNome = responsavel.getResponsavel().getNome();
        }
    }
}