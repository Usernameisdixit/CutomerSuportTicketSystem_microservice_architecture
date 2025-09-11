package com.hex.ticket_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Table(name = "ticket_history")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TicketHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ticket_id", nullable = false)
    private Ticket ticket;

    private String action;
    private String oldValue;
    private String newValue;

    private String comment;


    private LocalDateTime updatedAt;

//    @ManyToOne
//    @JoinColumn(name = "updated_by")
//    private User updatedBy;

     private Long updatedBy;

}
