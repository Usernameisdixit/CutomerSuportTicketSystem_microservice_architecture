package com.hex.ticket_service.exception;

public class InvalidTicketDataException extends RuntimeException{

    public InvalidTicketDataException(String message)
    {
        super(message);
    }
}
