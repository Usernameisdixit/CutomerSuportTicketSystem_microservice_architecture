package com.hex.ticket_service.exception;

public class UnauthorizedActionException extends RuntimeException {

    public UnauthorizedActionException(String message)
    {
        super(message);
    }
}
