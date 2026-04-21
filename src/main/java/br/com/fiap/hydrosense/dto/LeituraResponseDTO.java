package br.com.fiap.hydrosense.dto;

import br.com.fiap.hydrosense.model.RegistroLeitura;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO de resposta para registro de leitura
 */
public record LeituraResponseDTO(
        Long id,
        Long unidadeId,
        BigDecimal volumeM3,
        LocalDateTime dataHoraLeitura,
        String fonteLeitura,
        String observacao
) {
    public LeituraResponseDTO(RegistroLeitura leitura) {
        this(
                leitura.getId(),
                leitura.getUnidadeConsumo().getId(),
                leitura.getVolumeM3(),
                leitura.getDataHoraLeitura(),
                leitura.getFonteLeitura(),
                leitura.getObservacao()
        );
    }
}
