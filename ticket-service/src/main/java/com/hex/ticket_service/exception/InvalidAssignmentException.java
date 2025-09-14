package com.hex.ticket_service.exception;

import jakarta.persistence.criteria.CriteriaBuilder;

public class InvalidAssignmentException extends RuntimeException {

    public InvalidAssignmentException(String message)
    {
        super(message);
    }
}
