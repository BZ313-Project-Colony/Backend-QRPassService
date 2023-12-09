package com.group1.QRPass.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.UniqueElements;

public record CreateTicketRequest(@NotNull(message = "Event info cannot be null!")
                                  Long eventId,
                                  @NotNull(message = "Name cannot be null!")
                                  @NotBlank(message = "Name cannot be blank!")
                                  String name,
                                  @NotNull(message = "Surname cannot be null!")
                                  @NotBlank(message = "Surname cannot be blank!")
                                  String surname,
                                  @NotNull(message = "Email cannot be null!")
                                  @NotBlank(message = "Email cannot be blank!")
                                  String email) {
}
