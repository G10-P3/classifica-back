package br.com.g10.BEM.exceptions;

public class ExamAlreadyExistsException extends RuntimeException {
    public ExamAlreadyExistsException(String message) {
        super(message);
    }
}
