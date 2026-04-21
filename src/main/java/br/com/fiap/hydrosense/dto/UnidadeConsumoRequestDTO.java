package br.com.fiap.hydrosense.dto;

import br.com.fiap.hydrosense.model.TipoUnidade;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

/**
 * DTO para criar/atualizar uma unidade de consumo
 */
public record UnidadeConsumoRequestDTO(
        @NotBlank(message = "O endereco e obrigatorio")
        @Size(max = 255, message = "O endereco deve ter no maximo 255 caracteres")
        String endereco,

        @NotNull(message = "O tipo de unidade e obrigatorio")
        TipoUnidade tipo,

        @Min(value = 0, message = "O numero de habitantes deve ser positivo")
        Integer numeroHabitantes,

        @DecimalMin(value = "0.0", message = "A area deve ser positiva")
        BigDecimal areaM2
) {}
