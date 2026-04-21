package br.com.fiap.hydrosense.exception;

/**
 * Excecao lancada quando uma regra de negocio e violada
 */
public class BusinessException extends RuntimeException {

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }
}
