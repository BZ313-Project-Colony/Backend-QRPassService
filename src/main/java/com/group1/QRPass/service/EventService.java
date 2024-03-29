package com.group1.QRPass.service;

import com.group1.QRPass.dto.converter.EventDtoConverter;
import com.group1.QRPass.dto.request.CreateEventRequest;
import com.group1.QRPass.dto.response.EventCreatedResponse;
import com.group1.QRPass.dto.response.GetEventResponse;
import com.group1.QRPass.exception.EventNotFoundException;
import com.group1.QRPass.model.Event;
import com.group1.QRPass.repository.EventRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
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

    public GetEventResponse getEventById(Long eventId) {
        return eventDtoConverter.convertToGetEventResponse(findEventById(eventId));
    }

    public void disableEventById(Long eventId){
       Event eventToDisable = findEventById(eventId);
       eventToDisable.setActive(false);
       eventRepository.save(eventToDisable);
    }

    public void enableEventById(Long eventId){
        Event eventToEnable = findEventById(eventId);
        eventToEnable.setActive(true);
        eventRepository.save(eventToEnable);
    }

    protected Event findEventById(Long eventId){
        Optional<Event> event = eventRepository.findEventById(eventId);
        if (!event.isPresent()){
            throw new EventNotFoundException("There is no event registered in the system with the given Id");
        }
        return event.get();
    }

    protected void checkIfEventExist(Long eventId){
        if (eventRepository.findEventById(eventId).isEmpty()){
            throw new EventNotFoundException("There is no event registered in the system with the given Id");
        }
    }

    protected void deleteEvent(Event event){
        eventRepository.delete(event);
    }



}
