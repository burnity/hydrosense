package br.com.fiap.hydrosense.model;

/**
 * Enum que representa os tipos de unidades de consumo
 */
public enum TipoUnidade {
    RESIDENCIAL("Residencial"),
    COMERCIAL("Comercial");

    private final String descricao;

    TipoUnidade(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
