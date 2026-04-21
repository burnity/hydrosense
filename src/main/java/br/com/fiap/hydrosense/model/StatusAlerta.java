package br.com.fiap.hydrosense.model;

/**
 * Enum que representa os status de um alerta
 */
public enum StatusAlerta {
    ATIVO("Ativo - Aguardando acao"),
    RESOLVIDO("Resolvido - Problema solucionado"),
    IGNORADO("Ignorado - Alerta desconsiderado");

    private final String descricao;

    StatusAlerta(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
