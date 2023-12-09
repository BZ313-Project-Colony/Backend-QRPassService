package com.group1.QRPass.model;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.List;

@Entity
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private Timestamp timestamp;
    private String place;
    private String imageUrl;
    private Integer maxParticipantNumber;
    private boolean isActive;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Ticket> ticketList;

    public Event() {
    }

    public Event(Long id, String title, Timestamp timestamp, String place, String imageUrl, Integer maxParticipantNumber) {
        this.id = id;
        this.title = title;
        this.timestamp = timestamp;
        this.place = place;
        this.imageUrl = imageUrl;
        this.maxParticipantNumber = maxParticipantNumber;
        this.isActive = true;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Integer getMaxParticipantNumber() {
        return maxParticipantNumber;
    }

    public void setMaxParticipantNumber(Integer maxParticipantNumber) {
        this.maxParticipantNumber = maxParticipantNumber;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public List<Ticket> getTicketList() {
        return ticketList;
    }

    public void setTicketList(List<Ticket> ticketList) {
        this.ticketList = ticketList;
    }
}
