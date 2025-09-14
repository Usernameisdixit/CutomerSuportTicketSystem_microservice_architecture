package com.hex.ticket_service.exception;

public class TicketNotFoundException extends RuntimeException {

    public TicketNotFoundException(String message)
    {
        super(message);
    }
}
