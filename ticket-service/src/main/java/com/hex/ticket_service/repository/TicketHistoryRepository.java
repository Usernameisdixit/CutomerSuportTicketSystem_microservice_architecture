package com.hex.ticket_service.repository;

import com.hex.ticket_service.entity.TicketHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketHistoryRepository extends JpaRepository<TicketHistory, Long> {

    List<TicketHistory> findByTicketIdOrderByUpdatedAtDesc(Long ticketId);
}
