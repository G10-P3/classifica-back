package br.com.g10.BEM.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ClassAlreadyExistsException.class)
    public ResponseEntity<String> handleClassAlreadyExistsException(ClassAlreadyExistsException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        if (ex.getBindingResult().getFieldError() != null) {
        return ResponseEntity.badRequest().body("Erro de validação: " + ex.getBindingResult().getFieldError().getDefaultMessage());
        }
        return ResponseEntity.badRequest().body("Erro de validação genérico.");

}

}
