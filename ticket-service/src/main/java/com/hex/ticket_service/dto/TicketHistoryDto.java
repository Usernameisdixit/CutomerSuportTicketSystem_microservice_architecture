package com.hex.ticket_service.dto;

import java.time.LocalDateTime;

public record TicketHistoryDto(
        Long id,
        Long ticketId,
        String action,
        String oldValue,
        String newValue,
        String comment,
        LocalDateTime updatedAt,
        Long updatedBy
) {
}
