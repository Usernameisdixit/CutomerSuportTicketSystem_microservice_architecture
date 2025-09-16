package com.hex.user_service.dto;

import java.time.LocalDateTime;

public record ApiError(String message,
                       String path,
                       int status,
                       //@JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd' T 'HH:mm:ss.SSS")
                       LocalDateTime timestamp) {
}
