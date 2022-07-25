package com.nl.cgi.bff.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServiceException extends RuntimeException {

    protected ErrorDetail errorDetail;
    protected String overrideErrorCode;

    public ServiceException(ErrorDetail errorDetail, String errorMessage, Throwable throwable) {
        super(errorMessage, throwable);
        this.errorDetail = errorDetail;
    }

    public ServiceException(ErrorDetail errorDetail, String errorMessage) {
        super(errorMessage);
        this.errorDetail = errorDetail;
    }

    public ServiceException(String errorMessage) {
        super(errorMessage);
    }
}
