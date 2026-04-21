package br.com.fiap.hydrosense.dto;

import br.com.fiap.hydrosense.model.Alerta;
import br.com.fiap.hydrosense.model.StatusAlerta;

import java.time.LocalDateTime;

/**
 * DTO de resposta para alertas
 */
public record AlertaResponseDTO(
        Long id,
        Long unidadeId,
        Long leituraId,
        String tipoAlerta,
        String mensagem,
        StatusAlerta status,
        LocalDateTime dataCriacao,
        LocalDateTime dataResolucao
) {
    public AlertaResponseDTO(Alerta alerta) {
        this(
                alerta.getId(),
                alerta.getUnidadeConsumo().getId(),
                alerta.getRegistroLeitura() != null ? alerta.getRegistroLeitura().getId() : null,
                alerta.getTipoAlerta(),
                alerta.getMensagem(),
                alerta.getStatus(),
                alerta.getDataCriacao(),
                alerta.getDataResolucao()
        );
    }
}
