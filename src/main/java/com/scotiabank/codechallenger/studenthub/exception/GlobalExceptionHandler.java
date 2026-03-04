package com.scotiabank.codechallenger.studenthub.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Mono;

import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(StudentAlreadyExistsException.class)
    public Mono<ErrorResponse> handleStudentAlreadyExists(
            StudentAlreadyExistsException ex) {

        return Mono.just(
                ErrorResponse.builder()
                        .code(ex.getCode())
                        .message(ex.getMessage())
                        .status(HttpStatus.CONFLICT.value())
                        .timestamp(Instant.now())
                        .build()
        );
    }
}