package br.com.fiap.hydrosense.dto;

import br.com.fiap.hydrosense.model.TipoUnidade;
import br.com.fiap.hydrosense.model.UnidadeConsumo;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO de resposta para unidade de consumo
 */
public record UnidadeConsumoResponseDTO(
        Long id,
        String endereco,
        TipoUnidade tipo,
        Integer numeroHabitantes,
        BigDecimal areaM2,
        LocalDateTime dataCadastro,
        Boolean ativo
) {
    public UnidadeConsumoResponseDTO(UnidadeConsumo unidade) {
        this(
                unidade.getId(),
                unidade.getEndereco(),
                unidade.getTipo(),
                unidade.getNumeroHabitantes(),
                unidade.getAreaM2(),
                unidade.getDataCadastro(),
                unidade.getAtivo()
        );
    }
}
