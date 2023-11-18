package com.group1.QRPass.service;

import com.group1.QRPass.dto.converter.EventDtoConverter;
import com.group1.QRPass.dto.request.CreateEventRequest;
import com.group1.QRPass.dto.response.EventCreatedResponse;
import com.group1.QRPass.dto.response.GetEventResponse;
import com.group1.QRPass.model.Event;
import com.group1.QRPass.repository.EventRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventService {
    private final EventRepository eventRepository;
    private final EventDtoConverter eventDtoConverter;

    public EventService(EventRepository eventRepository, EventDtoConverter eventDtoConverter) {
        this.eventRepository = eventRepository;
        this.eventDtoConverter = eventDtoConverter;
    }

    public EventCreatedResponse createEvent(CreateEventRequest createEventRequest) {
        Event event = new Event(null, createEventRequest.title(), createEventRequest.time(),
                createEventRequest.place(),
                createEventRequest.imageUrl(),
                createEventRequest.maxParticipantNumber());
        return eventDtoConverter.convertToEventCreatedResponse(eventRepository.save(event));
    }

    public List<GetEventResponse> getAllEvent(){
        List<Event> events = eventRepository.findAll();
        return events.stream().map(eventDtoConverter::convertToGetEventResponse).collect(Collectors.toList());
    }
}
