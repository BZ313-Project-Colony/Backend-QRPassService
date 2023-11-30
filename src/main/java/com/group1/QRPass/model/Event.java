package com.group1.QRPass.model;

import jakarta.persistence.*;

import java.sql.Timestamp;

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

    public Event() {
    }

    public Event(Long id, String title, Timestamp timestamp, String place, String imageUrl, Integer maxParticipantNumber) {
        this.id = id;
        this.title = title;
        this.timestamp = timestamp;
        this.place = place;
        this.imageUrl = imageUrl;
        this.maxParticipantNumber = maxParticipantNumber;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", timestamp=" + timestamp +
                ", place='" + place + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", maxParticipantNumber=" + maxParticipantNumber +
                '}';
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
}
