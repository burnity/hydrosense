package br.com.fiap.hydrosense.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO para registrar uma nova leitura de consumo
 */
public record LeituraRequestDTO(
        @NotNull(message = "O ID da unidade e obrigatorio")
        Long unidadeId,

        @NotNull(message = "O volume de agua e obrigatorio")
        @DecimalMin(value = "0.001", message = "O volume deve ser maior que zero")
        BigDecimal volumeM3,

        LocalDateTime dataHoraLeitura,

        String observacao
) {}
