package com.hex.ticket_service.event;

import com.hex.ticket_service.dto.TicketDtos;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import static com.hex.ticket_service.config.RabbitMQConfig.EXCHANGE;
import static com.hex.ticket_service.config.RabbitMQConfig.ROUTING_KEY;

@Component
@AllArgsConstructor
public class TicketEventPublisher {
    private  final RabbitTemplate rabbitTemplate;

    public void publishTicketCreated(TicketDtos.TicketResponse ticket)
    {
        rabbitTemplate.convertAndSend(EXCHANGE,ROUTING_KEY,"New Ticket Created: "+ticket.id()+" by "+ticket.createdBy());
    }

    public  void publishTicketUpdated(TicketDtos.TicketResponse ticket)
    {
        rabbitTemplate.convertAndSend(EXCHANGE,ROUTING_KEY,"Ticket Updated: "+ticket.id()+" Status: "+ticket.status());
    }

}
