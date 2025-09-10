package com.hex.ticket_service.service;

import com.hex.ticket_service.dto.TicketDtos;
import com.hex.ticket_service.entity.Ticket;
import com.hex.ticket_service.entity.TicketStatus;
//import com.hex.ticket_service.entity.User;
//import com.hex.ticket_service.repository.TicketHistoryRepository;
import com.hex.ticket_service.repository.TicketRepository;
//import com.hex.ticket_service.repository.UserRepository;
import com.hex.ticket_service.service.client.UserClient;
import com.hex.ticket_service.service.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@AllArgsConstructor
@Service
@Slf4j
public class TicketService {

    private final TicketRepository ticketRepository;
    private final UserClient userClient;
    //private final UserRepository userRepository;
    //private final TicketHistoryService ticketHistoryService;
                                                                                                                                                                                                                                                        

    public TicketDtos.TicketResponse createTicket(TicketDtos.CreateTicketRequest request) {
        log.info("Creating Ticket with title:{}", request.title());
        log.debug("Full Ticket request Payload:{}", request);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        log.info("Auth value: {}",auth);
        //User creator = userRepository.findByUsername(auth.getName()).orElseThrow();
        UserDto creator=userClient.getUserByUsername(auth.getName());
        log.info("Creatror: {}",creator);
        Ticket ticket = Ticket.builder().title(request.title())
                .description(request.description())
                .status(TicketStatus.OPEN)
                .priority(request.priority())
                .createdBy(creator.id())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        Ticket saved = ticketRepository.save(ticket);
        log.info("Saved value of Ticket: {}",saved);
        return mapToResponse(saved);

    }


    public List<TicketDtos.TicketResponse> getCustomerTickets() {
        return ticketRepository.findAll().stream().map(this::mapToResponse).collect(Collectors.toList());

    }

    public List<TicketDtos.TicketResponse> getAllTickets() {
        return ticketRepository.findAll().stream().map(this::mapToResponse).collect(Collectors.toList());

    }

//    public TicketDtos.TicketResponse updateTicket(Long id, TicketDtos.UpdateTicketRequest req) {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        String currentUser = auth.getName();
//        boolean isAdmin = auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
//
//        Ticket ticket = ticketRepository.findById(id).orElseThrow();
//
//        if (!isAdmin && auth.getAuthorities().stream().noneMatch(a -> a.getAuthority().equals("ROLE_AGENT"))) {
//            throw new RuntimeException("You are not allowed to update tickets.");
//        }
//
////in the req we are assigning the assignedTo value so we have kept !=null
//        if (req.assignedTo() != null) {
//            String oldAssignee = ticket.getAssignedTo() != null ? ticket.getAssignedTo().getUsername() : null;
//            if (isAdmin) {
//                User assignee = userRepository.findByUsername(req.assignedTo()).orElseThrow(() -> new RuntimeException("User not found"));
//                ticket.setAssignedTo(assignee);
//            } else {
//
//
//                if (!req.assignedTo().equals(currentUser)) {
//                    throw new RuntimeException(("Agents can only assign tickets to themselves."));
//                }
//                User assignee = userRepository.findByUsername(currentUser).orElseThrow();
//                ticket.setAssignedTo(assignee);
//            }
//
//            String newAssignee = ticket.getAssignedTo() != null ? ticket.getAssignedTo().getUsername() : null;
//            ticketHistoryService.logHistory(ticket, "ASSIGNED_CHANGE", oldAssignee, newAssignee, req.comment(), currentUser);
//        }
//        if (req.status() != null && !req.status().equals(ticket.getStatus())) {
//            String oldStatus = ticket.getStatus() != null ? ticket.getStatus().name() : null;
//            ticket.setStatus(req.status());
//            String newStaus = ticket.getStatus() != null ? ticket.getStatus().name() : null;
//
//            ticketHistoryService.logHistory(ticket, "STATUS_CHANGED", oldStatus, newStaus, req.comment(), currentUser);
//
//        }
//        return mapToResponse(ticketRepository.save(ticket));
//    }


    private TicketDtos.TicketResponse mapToResponse(Ticket ticket) {
        //UserDto creator=userClient.getUserById(ticket.getCreatedBy());
        //UserDto assignee=userClient.getUserById(ticket.getAssignedTo());
        //log.info("Creator: {}",creator);
        //log.info("Assignee: {}",assignee);

        return new TicketDtos.TicketResponse(ticket.getId(), ticket.getTitle(), ticket.getDescription(), ticket.getStatus().name(), ticket.getPriority().name(),
//                ticket.getCreatedBy() != null ? ticket.getCreatedBy().getUsername() : null,
//                ticket.getAssignedTo() != null ? ticket.getAssignedTo().getUsername() : null);
    ticket.getCreatedBy(),
                ticket.getAssignedTo());

    }


}
