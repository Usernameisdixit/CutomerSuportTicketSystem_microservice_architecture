package com.hex.ticket_service.dto;

import com.hex.ticket_service.entity.Priority;
import com.hex.ticket_service.entity.TicketStatus;
import com.hex.ticket_service.service.dto.UserDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class TicketDtos {

    public record CreateTicketRequest(
            @NotBlank String title,
            @NotBlank String description,
            @NotNull Priority priority
    ) {
    }


    public record UpdateTicketRequest(String title,
                                      String description,
                                      TicketStatus status,
                                      String priority,
                                      String comment,
                                      String assignedTo) {
    }


    public record TicketResponse(Long id, String title, String description, String status, String priority,
                                 Long createdBy,
                                 Long assignedTo) {
    }

}
