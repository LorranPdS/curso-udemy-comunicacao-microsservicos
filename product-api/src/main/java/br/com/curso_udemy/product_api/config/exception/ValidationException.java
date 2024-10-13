package br.com.curso_udemy.product_api.config.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/*
    1. Colocamos esse response sempre como bad request nesse tipo de exception
    2. também estenderemos sempre de RuntimeException para que seja possível capturarmos a exceção
        pela nossa classe
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ValidationException extends RuntimeException {

    public ValidationException(String message){
        super(message); // aqui estamos estendendo o construtor do RuntimeException
    }
}
