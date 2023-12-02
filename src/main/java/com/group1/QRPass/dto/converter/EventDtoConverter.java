package com.group1.QRPass.dto.converter;

import com.group1.QRPass.dto.response.EventCreatedResponse;
import com.group1.QRPass.dto.response.GetEventResponse;
import com.group1.QRPass.model.Event;
import org.springframework.stereotype.Component;

@Component
public class EventDtoConverter {
    public EventCreatedResponse convertToEventCreatedResponse(Event event){
        return new EventCreatedResponse(event.getId());
    }

    public GetEventResponse convertToGetEventResponse(Event event){
        return new GetEventResponse(event.getId(), event.getTitle(), event.getPlace(),
                event.getTimestamp(), event.isActive());
    }
}
