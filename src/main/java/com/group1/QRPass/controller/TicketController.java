package com.group1.QRPass.controller;

import com.group1.QRPass.dto.request.CreateConfirmTicketRequest;
import com.group1.QRPass.dto.response.GetTicketResponse;
import com.group1.QRPass.service.TicketService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/tickets")
public class TicketController {
    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping("/{ticketId:\\d+}")
    public ResponseEntity<GetTicketResponse> getTicketByTicketId(@PathVariable Long ticketId){
        return ResponseEntity.ok(ticketService.getTicketByTicketId(ticketId));
    }

    @PostMapping("/confirm")
    public ResponseEntity<String> confirmTicket(@RequestBody CreateConfirmTicketRequest createConfirmTicketRequest){
        ticketService.confirmTicket(createConfirmTicketRequest);
        return new ResponseEntity<>("Ticket is confirmed", HttpStatus.OK);
    }

    @PostMapping("/{ticketId:\\d+}/disable")
    public ResponseEntity<String> disableTicket(@PathVariable Long ticketId){
        ticketService.disableTicket(ticketId);
        return new ResponseEntity<>("Ticket is disabled", HttpStatus.OK);
    }
}
