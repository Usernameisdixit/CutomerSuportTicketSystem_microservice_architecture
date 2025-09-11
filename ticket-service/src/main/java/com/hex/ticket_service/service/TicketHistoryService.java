package com.hex.ticket_service.service;

import com.hex.ticket_service.dto.TicketHistoryDto;
import com.hex.ticket_service.entity.Ticket;
import com.hex.ticket_service.entity.TicketHistory;
//import com.hex.ticket_service.entity.User;
import com.hex.ticket_service.repository.TicketHistoryRepository;
//import com.hex.ticket_service.repository.UserRepository;
import com.hex.ticket_service.service.client.UserClient;
import com.hex.ticket_service.service.dto.UserDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class TicketHistoryService {

    private final TicketHistoryRepository ticketHistoryRepository;
    //private final UserRepository userRepository;
    private final UserClient userClient;


    public void logHistory(Ticket ticket, String action, String oldValue, String  newValue, String comment, String username) {
        //User updater = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
        UserDto updater = userClient.getUserByUsername(username);

        TicketHistory history = TicketHistory.builder()
                .ticket(ticket)
                .action(action)
                .oldValue(oldValue)
                .newValue(newValue)
                .comment(comment)
                .updatedAt(LocalDateTime.now())
                .updatedBy(updater.id())
                .build();

        ticketHistoryRepository.save(history);
    }

    public List<TicketHistoryDto> getHistory(Long ticketId) {
        return ticketHistoryRepository.findByTicketIdOrderByUpdatedAtDesc(ticketId)
                .stream().map(h -> new TicketHistoryDto(h.getId(),
                        h.getTicket().getId(),
                        h.getAction(),
                        h.getOldValue(),
                        h.getNewValue(),
                        h.getComment(),
                        h.getUpdatedAt(),
                        h.getUpdatedBy())).toList();

    }


}
