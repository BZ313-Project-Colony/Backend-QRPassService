package com.group1.QRPass.service;

import com.group1.QRPass.dto.converter.EventDtoConverter;
import com.group1.QRPass.repository.EventRepository;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
public class EventServiceTest {

    private EventRepository eventRepository;
    private EventDtoConverter eventDtoConverter;
    private EventService eventService;

    @BeforeEach
    void setUp(){
        eventRepository = mock(EventRepository.class);
        eventDtoConverter = mock(EventDtoConverter.class);
        eventService = new EventService(eventRepository, eventDtoConverter);
    }

}
