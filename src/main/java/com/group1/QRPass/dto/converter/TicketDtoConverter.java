package com.group1.QRPass.dto.converter;


import com.group1.QRPass.dto.response.GetTicketResponse;
import com.group1.QRPass.model.Ticket;
import org.springframework.stereotype.Component;

@Component
public class TicketDtoConverter {
    public GetTicketResponse convertToGetTicketResponse(Ticket ticket){
        return new GetTicketResponse(ticket.getId(), ticket.getParticipantName(),
                ticket.getParticipantSurname(),
                ticket.getParticipantEmail(),
                ticket.getRegisteredDate(),
                ticket.isConfirmed());
    }

}
