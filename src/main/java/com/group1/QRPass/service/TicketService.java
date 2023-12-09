package com.group1.QRPass.service;


import com.google.zxing.WriterException;
import com.group1.QRPass.core.service.EmailService;
import com.group1.QRPass.core.service.QRCodeGenerator;
import com.group1.QRPass.core.utils.EmailValidation;
import com.group1.QRPass.dto.converter.TicketDtoConverter;
import com.group1.QRPass.dto.request.CreateConfirmTicketRequest;
import com.group1.QRPass.dto.request.CreateTicketRequest;
import com.group1.QRPass.dto.response.GetTicketResponse;
import com.group1.QRPass.exception.EmailNotValidException;
import com.group1.QRPass.exception.TicketNotFoundException;
import com.group1.QRPass.exception.TicketUniqueValidateException;
import com.group1.QRPass.model.Event;
import com.group1.QRPass.model.Ticket;
import com.group1.QRPass.repository.TicketRepository;
import jakarta.mail.MessagingException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TicketService {
    private final TicketRepository ticketRepository;
    private final TicketDtoConverter ticketDtoConverter;
    private final EventService eventService;
    private final EmailService emailService;

    public TicketService(TicketRepository ticketRepository, TicketDtoConverter ticketDtoConverter, EventService eventService, EmailService emailService) {
        this.ticketRepository = ticketRepository;
        this.ticketDtoConverter = ticketDtoConverter;
        this.eventService = eventService;
        this.emailService = emailService;
    }
    private void validateEmail(String email){
        if (!EmailValidation.patternMatches(email))
            throw new EmailNotValidException("Email address is not valid!");
    }

    private void validateUniqueConstraint(Long id, String email){
        List<Ticket> ticketList = ticketRepository.findAllByEvent_Id(id)
                .stream()
                .filter(ticket -> ticket.getParticipantEmail().equals(email))
                .toList();
        if (ticketList.size() > 0)
            throw new TicketUniqueValidateException("There is already a ticket registered for this email!");
    }
    public void createTicket(CreateTicketRequest createTicketRequest) throws MessagingException, IOException, WriterException {
        validateEmail(createTicketRequest.email());
        validateUniqueConstraint(createTicketRequest.eventId(), createTicketRequest.email());
        String qrCodeText = String.format("Event ID: %s\nName: %s\nSurname: %s\nEmail: %s",
                createTicketRequest.eventId(), createTicketRequest.name(), createTicketRequest.surname(),
                createTicketRequest.email());
        byte[] qrCode = QRCodeGenerator.getQRCodeImage(qrCodeText, 200, 200);
        emailService.sendEmail(eventService.findEventById(createTicketRequest.eventId()),
                createTicketRequest.email(),
                createTicketRequest.name(),
                createTicketRequest.surname(),
                qrCode);
        Ticket ticket = new Ticket(null, createTicketRequest.name(),createTicketRequest.surname(),
                createTicketRequest.email(),
                Timestamp.valueOf(LocalDateTime.now()),
                eventService.findEventById(createTicketRequest.eventId()));
        ticketRepository.save(ticket);
    }

    public List<GetTicketResponse> getAllTicketByEventId(Long eventId) {
        eventService.checkIfEventExist(eventId);
        List<Ticket> ticketList = ticketRepository.findAllByEvent_Id(eventId);
        return ticketList.stream().map(ticketDtoConverter::convertToGetTicketResponse)
                .collect(Collectors.toList());
    }

    public GetTicketResponse getTicketByTicketId(Long ticketId) {
        return ticketDtoConverter.convertToGetTicketResponse(ticketRepository.findTicketById(ticketId)
                .orElseThrow(() -> new TicketNotFoundException("There is no ticket the system with the given Id")));
    }

    public void confirmTicket(CreateConfirmTicketRequest createConfirmTicketRequest) {
       Ticket ticketToConfirmed = ticketRepository
               .findTicketByEvent_IdAndAndParticipantEmail(createConfirmTicketRequest.eventId(),
                       createConfirmTicketRequest.email())
               .orElseThrow(() -> new TicketNotFoundException("There is no ticket the system with the given info"));
       ticketToConfirmed.setConfirmed(true);
       ticketRepository.save(ticketToConfirmed);
    }

    public void disableTicket(Long ticketId) {
        Ticket ticketToDisabled = ticketRepository.findTicketById(ticketId)
                .orElseThrow(() -> new TicketNotFoundException("There is no ticket the system with the given info"));
        ticketToDisabled.setConfirmed(false);
        ticketRepository.save(ticketToDisabled);
    }
    @Transactional
    public void deleteAllTicketsByEventId(Long eventId){
        Event event = eventService.findEventById(eventId);
        ticketRepository.deleteAllByEvent_Id(eventId);
        eventService.deleteEvent(event);
    }
}
