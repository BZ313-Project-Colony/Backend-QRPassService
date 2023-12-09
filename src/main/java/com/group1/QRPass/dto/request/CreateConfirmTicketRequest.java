package com.group1.QRPass.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateConfirmTicketRequest(
        @NotNull(message = "Event info cannot be null!")
        Long eventId,
        @NotNull(message = "Email cannot be null!")
        @NotBlank(message = "Email cannot be blank!")
        String email) {
}
