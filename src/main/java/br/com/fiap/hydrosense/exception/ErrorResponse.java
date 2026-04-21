package br.com.fiap.hydrosense.exception;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Classe para padronizar respostas de erro da API
 */
public record ErrorResponse(
        LocalDateTime timestamp,
        Integer status,
        String error,
        String message,
        String path,
        List<String> details
) {
    public ErrorResponse(Integer status, String error, String message, String path) {
        this(LocalDateTime.now(), status, error, message, path, null);
    }

    public ErrorResponse(Integer status, String error, String message, String path, List<String> details) {
        this(LocalDateTime.now(), status, error, message, path, details);
    }
}
