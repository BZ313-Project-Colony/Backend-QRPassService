package com.group1.QRPass.controller;

import com.google.zxing.WriterException;
import com.group1.QRPass.dto.request.CreateEventRequest;
import com.group1.QRPass.dto.request.CreateTicketRequest;
import com.group1.QRPass.dto.response.EventCreatedResponse;
import com.group1.QRPass.dto.response.GetEventResponse;
import com.group1.QRPass.dto.response.GetTicketResponse;
import com.group1.QRPass.service.EventService;
import com.group1.QRPass.service.TicketService;
import jakarta.mail.MessagingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/v1/events")
@CrossOrigin("*")
public class EventController {
    private final EventService eventService;
    private final TicketService ticketService;

    public EventController(EventService eventService, TicketService ticketService) {

        this.eventService = eventService;
        this.ticketService = ticketService;
    }

    @PostMapping
    public ResponseEntity<EventCreatedResponse> createEvent(@RequestBody CreateEventRequest createEventRequest){
        return ResponseEntity.ok(eventService.createEvent(createEventRequest));
    }

    @GetMapping
    public ResponseEntity<List<GetEventResponse>> getAllEvent(){
        return ResponseEntity.ok(eventService.getAllEvent());
    }
    @GetMapping("/{eventId:\\d+}")
    public ResponseEntity<GetEventResponse> getEventById(@PathVariable Long eventId){
        return ResponseEntity.ok(eventService.getEventById(eventId));
    }

    @PostMapping("/{eventId:\\d+}/disable")
    public ResponseEntity<String> disableEvent(@PathVariable Long eventId){
        eventService.disableEventById(eventId);
        return new ResponseEntity<>("Event disabled successfully", HttpStatus.OK);
    }
    @PostMapping("/{eventId:\\d+}/enable")
    public ResponseEntity<String> enableEvent(@PathVariable Long eventId){
        eventService.enableEventById(eventId);
        return new ResponseEntity<>("Event enabled successfully", HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<String> createTicket(@RequestBody CreateTicketRequest createTicketRequest) throws MessagingException, IOException, WriterException {
        ticketService.createTicket(createTicketRequest);
        return new ResponseEntity<>("Your ticket is created successfully", HttpStatus.CREATED);
    }

    @GetMapping("/{eventId:\\d+}/tickets")
    public ResponseEntity<List<GetTicketResponse>> getTicketsByEvent(@PathVariable Long eventId){
        return ResponseEntity.ok(ticketService.getAllTicketByEventId(eventId));
    }

    @DeleteMapping("/{eventId:\\d+}")
    public ResponseEntity<String> deleteEventById(@PathVariable Long eventId){
        ticketService.deleteAllTicketsByEventId(eventId);
        return new ResponseEntity<>("The event and related tickets are deleted successfully", HttpStatus.OK);
    }



}
