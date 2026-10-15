package br.cefetmg.grow.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AvaliacaoResponseDTO {
    private Long id;
    private Integer nota;
    private String comentario;
    private LocalDateTime dataAvaliacao;
    private Long especieId;
    private String nomeEspecie;
    private Long usuarioId;
    private String nomeUsuario;
}