package br.com.fiap.hydrosense.dto;

import java.math.BigDecimal;

/**
 * DTO para resumo de consumo de uma unidade
 */
public record ConsumoResumoDTO(
        Long unidadeId,
        String endereco,
        BigDecimal consumoTotalM3,
        Integer numeroLeituras,
        BigDecimal mediaDiariaM3,
        Long alertasAtivos
) {}
