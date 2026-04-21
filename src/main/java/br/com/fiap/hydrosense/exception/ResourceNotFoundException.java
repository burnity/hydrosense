package br.com.fiap.hydrosense.exception;

/**
 * Excecao lancada quando um recurso nao e encontrado
 */
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String resource, Long id) {
        super(String.format("%s com ID %d nao encontrado(a)", resource, id));
    }
}
