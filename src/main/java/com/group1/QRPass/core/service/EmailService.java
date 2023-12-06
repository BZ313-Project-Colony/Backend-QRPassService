package com.group1.QRPass.core.service;

import com.group1.QRPass.dto.request.CreateTicketRequest;
import com.group1.QRPass.model.Event;
import com.group1.QRPass.service.EventService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Value("${spring.mail.username}")
    private String emailFrom;
    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }
    public void sendEmail(Event event, String emailTo, String partName, String partSurname, byte[] qrCode) throws MessagingException {
        String subject = event.getTitle() +  " Ticket : Issued for " + partName + " " + partSurname ;
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(emailFrom);
        helper.setTo(emailTo);
        helper.setSubject(subject);
        helper.setText("sa");
        InputStreamSource qrCodeSource = new ByteArrayResource(qrCode);
        helper.addInline("qrCode", qrCodeSource, "image/png");
        mailSender.send(message);
    }
}
