package com.scotiabank.codechallenger.studenthub.exception;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class ErrorResponse {

    private String code;
    private String message;
    private int status;
    private Instant timestamp;
}