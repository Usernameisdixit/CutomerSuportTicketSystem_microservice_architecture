package com.hex.ticket_service.exception;

public class AccessDeniedException extends RuntimeException{

    public AccessDeniedException(String message)
    {
        super(message);
    }
}
