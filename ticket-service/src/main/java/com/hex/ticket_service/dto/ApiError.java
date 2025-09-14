package com.hex.ticket_service.dto;

import java.time.LocalDateTime;

public record ApiError (
    String message,
    String path,
    int status,
    LocalDateTime timestamp)
    {}
