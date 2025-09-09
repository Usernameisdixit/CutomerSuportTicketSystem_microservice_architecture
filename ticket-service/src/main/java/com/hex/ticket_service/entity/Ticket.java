package com.hex.ticket_service.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Table(name = "tickets")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Ticket {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, length = 2000)
    private String description;

    @Enumerated(EnumType.STRING)
    private TicketStatus status;

    @Enumerated(EnumType.STRING)
    private Priority priority;

//    @ManyToOne
//    @JoinColumn(name = "assigned_to")
//    private User assignedTo;
//
//
//    @ManyToOne
//    @JoinColumn(name = "created_by", nullable = false)
//    private User createdBy;

    private Long assignedTo;
    private Long createdBy;


    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


}
