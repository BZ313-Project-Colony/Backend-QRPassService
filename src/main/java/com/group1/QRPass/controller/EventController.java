package com.group1.QRPass.controller;

import com.group1.QRPass.dto.request.CreateEventRequest;
import com.group1.QRPass.dto.response.EventCreatedResponse;
import com.group1.QRPass.dto.response.GetEventResponse;
import com.group1.QRPass.service.EventService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/events")
public class EventController {
    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
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
}
