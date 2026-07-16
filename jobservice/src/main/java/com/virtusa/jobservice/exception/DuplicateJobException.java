package com.virtusa.jobservice.exception;

public class DuplicateJobException extends RuntimeException {
    public DuplicateJobException(String message) {
        super(message);
    }
}
