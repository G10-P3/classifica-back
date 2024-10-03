package br.com.g10.BEM.exceptions;

public class ClassAlreadyExistsException extends RuntimeException {
    public ClassAlreadyExistsException(String message) {
        super(message);
    }
}
