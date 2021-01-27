package com.erc.apicadastro.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class APIExceptionHandler {

    @ExceptionHandler(PessoaNaoEncontradaException.class)
    public ResponseEntity<String> pessoaNaoEncontradaExceptionHandler(PessoaNaoEncontradaException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<List> validationExceptionHandler(ConstraintViolationException exception) {
        List<String> erros = new ArrayList<>();
        exception.getConstraintViolations().forEach(erro -> erros.add(erro.getMessage()));
        return new ResponseEntity<>(erros, HttpStatus.BAD_REQUEST);
    }

}
