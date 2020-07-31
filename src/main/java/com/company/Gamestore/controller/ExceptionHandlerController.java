package com.company.Gamestore.controller;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.hateoas.VndErrors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;

import javax.xml.bind.ValidationException;
import java.io.FileNotFoundException;

@RestControllerAdvice
@RequestMapping(produces = "application/vnd.error+json")
public class ExceptionHandlerController {
    @ExceptionHandler(value = {IllegalArgumentException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<VndErrors> illegalArgumentException(IllegalArgumentException e, WebRequest request) {
        VndErrors error = new VndErrors(request.toString(), "Illegal Argument: " + e.getMessage());
        ResponseEntity<VndErrors> responseEntity = new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        return responseEntity;
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<VndErrors> methodArguemntNotValidException(MethodArgumentNotValidException e, WebRequest request) {
        VndErrors error = new VndErrors(request.toString(), "Bad Request: " + e.getMessage());
        ResponseEntity<VndErrors> responseEntity = new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        return responseEntity;
    }

    @ExceptionHandler(value = {FileNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<VndErrors> notFoundException(FileNotFoundException e, WebRequest request) {
        VndErrors error = new VndErrors(request.toString(), "Not found: " + e.getMessage());
        ResponseEntity<VndErrors> responseEntity = new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        return responseEntity;
    }

    //EmptyResultDataAccessException
    @ExceptionHandler(value = {EmptyResultDataAccessException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<VndErrors> emptyResultDataAccessException(EmptyResultDataAccessException e, WebRequest request) {
        VndErrors error = new VndErrors(request.toString(), "Not found: no data found for given input");
        ResponseEntity<VndErrors> responseEntity = new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        return responseEntity;
    }
}
