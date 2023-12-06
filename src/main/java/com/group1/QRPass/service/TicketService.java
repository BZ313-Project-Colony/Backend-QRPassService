package com.group1.QRPass.service;


import com.google.zxing.WriterException;
import com.group1.QRPass.core.service.EmailService;
import com.group1.QRPass.core.service.QRCodeGenerator;
import com.group1.QRPass.core.utils.EmailValidation;
import com.group1.QRPass.dto.request.CreateTicketRequest;
import com.group1.QRPass.exception.EmailNotValidException;
import com.group1.QRPass.repository.TicketRepository;
import jakarta.mail.MessagingException;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class TicketService {
    private final TicketRepository ticketRepository;
    private final EventService eventService;
    private final EmailService emailService;

    public TicketService(TicketRepository ticketRepository, EventService eventService, EmailService emailService) {
        this.ticketRepository = ticketRepository;
        this.eventService = eventService;
        this.emailService = emailService;
    }
    private void validateEmail(String email){
        if (!EmailValidation.patternMatches(email))
            throw new EmailNotValidException("Email address is not valid!");
    }

    public void createTicket(CreateTicketRequest createTicketRequest) throws MessagingException, IOException, WriterException {
        validateEmail(createTicketRequest.email());
        String qrCodeText = String.format("Event ID: %s\nName: %s\nSurname: %s\nEmail: %s",
                createTicketRequest.eventId(), createTicketRequest.name(), createTicketRequest.surname(),
                createTicketRequest.email());
        byte[] qrCode = QRCodeGenerator.getQRCodeImage(qrCodeText, 200, 200);
        emailService.sendEmail(eventService.findEventById(createTicketRequest.eventId()),
                createTicketRequest.email(),
                createTicketRequest.name(),
                createTicketRequest.surname(),
                qrCode);
    }
}
