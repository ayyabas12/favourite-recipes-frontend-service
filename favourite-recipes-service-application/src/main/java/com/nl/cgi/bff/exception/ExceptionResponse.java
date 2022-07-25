package com.nl.cgi.bff.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class ExceptionResponse {
    private String errorCode;
    private String errorMessage;
}
