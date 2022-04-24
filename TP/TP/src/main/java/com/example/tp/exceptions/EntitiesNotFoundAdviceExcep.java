package com.example.tp.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

//import jdk.internal.agent.resources.agent;

@ControllerAdvice
class NotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(EntitiesNotFoundExcep.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String entitiesNotFoundHandler(EntitiesNotFoundExcep ex) {
        return ex.getMessage();
    }
}
