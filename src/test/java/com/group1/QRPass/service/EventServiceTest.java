package com.group1.QRPass.service;

import com.group1.QRPass.dto.converter.EventDtoConverter;
import com.group1.QRPass.dto.response.GetEventResponse;
import com.group1.QRPass.model.Event;
import com.group1.QRPass.repository.EventRepository;
import org.hibernate.sql.ast.tree.expression.Collation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
public class EventServiceTest {

    private EventRepository eventRepository;
    private EventDtoConverter eventDtoConverter;
    private EventService eventService;

    @BeforeEach
    void setUp() {
        eventRepository = mock(EventRepository.class);
        eventDtoConverter = mock(EventDtoConverter.class);
        eventService = new EventService(eventRepository, eventDtoConverter);
    }

    @Test
    void getAllEvent_whenEventListExist_shouldReturnListOfGetEventResponse(){
        List<Event> eventList = Arrays.asList(new Event(), new Event());
        when(eventRepository.findAll()).thenReturn(eventList);
        when(eventDtoConverter.convertToGetEventResponse(new Event()))
                .thenReturn(new GetEventResponse(1L,"test",
                        Timestamp.valueOf(LocalDateTime.now())));
        List<GetEventResponse> expectedResult = eventList.stream()
                .map(eventDtoConverter::convertToGetEventResponse)
                .toList();
        List<GetEventResponse> actualResult = eventService.getAllEvent();
        assertEquals(expectedResult,actualResult);
    }

    @Test
    void getAllEvent_whenChatListEmpty_shouldReturnEmptyListOfGetEventResponse() {
        when(eventRepository.findAll()).thenReturn(Collections.emptyList());
        when(eventDtoConverter.convertToGetEventResponse(new Event()))
                .thenReturn(null);
        List<GetEventResponse> result = eventService.getAllEvent();
        assertEquals(Collections.emptyList(),result);
    }

}
