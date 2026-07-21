package br.cefetmg.grow.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

import br.cefetmg.grow.model.EspecieFavorita;

@Getter
@Setter
@NoArgsConstructor
public class EspecieFavoritaResponseDTO {

    @Schema(description = "ID do favorito")
    private Long id;

    @Schema(description = "Data em que foi favoritado")
    private LocalDate dataFavorito;

    @Schema(description = "ID do usuário")
    private Long usuarioId;

    @Schema(description = "Nome do usuário")
    private String usuarioNome;

    @Schema(description = "ID da espécie")
    private Long especieId;

    @Schema(description = "Nome popular da espécie")
    private String especieNomePopular;

    public EspecieFavoritaResponseDTO(EspecieFavorita favorito) {
        this.id = favorito.getId();
        this.dataFavorito = favorito.getDataFavorito();
        this.usuarioId = favorito.getUsuario().getId();
        this.usuarioNome = favorito.getUsuario().getNome();
        this.especieId = favorito.getEspeciePlanta().getId();
        this.especieNomePopular = favorito.getEspeciePlanta().getNomePopular();
    }
}