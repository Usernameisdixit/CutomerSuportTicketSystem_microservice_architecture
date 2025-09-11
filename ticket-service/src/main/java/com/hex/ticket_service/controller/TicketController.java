package com.hex.ticket_service.controller;

import com.hex.ticket_service.dto.TicketDtos;
import com.hex.ticket_service.dto.TicketHistoryDto;
import com.hex.ticket_service.service.TicketHistoryService;
import com.hex.ticket_service.service.TicketService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tickets")
@AllArgsConstructor
public class TicketController {

    private final TicketService ticketService;

    private final TicketHistoryService ticketHistoryService;


    @PostMapping
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<TicketDtos.TicketResponse> create(@Valid @RequestBody TicketDtos.CreateTicketRequest request) {
        return ResponseEntity.ok(ticketService.createTicket(request));
    }


    @GetMapping("/cus")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<List<TicketDtos.TicketResponse>> getCustomerTickets() {
        return ResponseEntity.ok(ticketService.getCustomerTickets());
    }


    @GetMapping
    @PreAuthorize("hasAnyRole('AGENT','ADMIN')")
    public ResponseEntity<List<TicketDtos.TicketResponse>> getAll() {
        return ResponseEntity.ok(ticketService.getAllTickets());
    }


    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('AGENT','ADMIN')")
    public ResponseEntity<TicketDtos.TicketResponse> updateTicket(@PathVariable Long id, @RequestBody TicketDtos.UpdateTicketRequest req) {
        return ResponseEntity.ok(ticketService.updateTicket(id, req));
    }


    @GetMapping("/{id}/history")
    @PreAuthorize("hasAnyRole('CUSTOMER','AGENT','ADMIN')")
    public List<TicketHistoryDto> getTicketHistory(@PathVariable Long id) {
        return ticketHistoryService.getHistory(id);
    }


}
