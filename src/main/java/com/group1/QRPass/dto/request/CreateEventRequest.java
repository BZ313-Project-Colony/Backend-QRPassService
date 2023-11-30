package com.group1.QRPass.dto.request;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.sql.Timestamp;

public record CreateEventRequest(@NotNull(message = "Title information cannot be Null!")
                                 @NotBlank(message = "Title information cannot be Blank!")
                                 String title,
                                 @NotNull(message = "Time information cannot be Null!")
                                 Timestamp time,
                                 @NotNull(message = "Place information cannot be Null!")
                                 @NotBlank(message = "Place information cannot be Blank!")
                                 String place,
                                 String imageUrl,
                                 @NotNull(message = "Maximum participant information cannot be Null!")
                                 Integer maxParticipantNumber) {
}
