package com.group1.QRPass.model;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String participantName;
    private String participantSurname;
    private String participantEmail;
    private Timestamp registeredDate;
    private boolean isConfirmed;
    @ManyToOne(fetch = FetchType.LAZY)
    private Event event;

    public Ticket() {
    }

    public Ticket(Long id, String participantName, String participantSurname, String participantEmail,
                  Timestamp registeredDate, boolean isConfirmed, Event event) {
        this.id = id;
        this.participantName = participantName;
        this.participantSurname = participantSurname;
        this.participantEmail = participantEmail;
        this.registeredDate = registeredDate;
        this.isConfirmed = isConfirmed;
        this.event = event;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getParticipantName() {
        return participantName;
    }

    public void setParticipantName(String participantName) {
        this.participantName = participantName;
    }

    public String getParticipantSurname() {
        return participantSurname;
    }

    public void setParticipantSurname(String participantSurname) {
        this.participantSurname = participantSurname;
    }

    public String getParticipantEmail() {
        return participantEmail;
    }

    public void setParticipantEmail(String participantEmail) {
        this.participantEmail = participantEmail;
    }

    public Timestamp getRegisteredDate() {
        return registeredDate;
    }

    public void setRegisteredDate(Timestamp registeredDate) {
        this.registeredDate = registeredDate;
    }

    public boolean isConfirmed() {
        return isConfirmed;
    }

    public void setConfirmed(boolean confirmed) {
        isConfirmed = confirmed;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }
}
