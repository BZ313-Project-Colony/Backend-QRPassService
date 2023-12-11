package com.group1.QRPass.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class EventNotActiveException extends RuntimeException{
    public EventNotActiveException(String message) {
        super(message);
    }
}
