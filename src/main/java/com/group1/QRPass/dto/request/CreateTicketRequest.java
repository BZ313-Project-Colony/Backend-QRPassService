package com.group1.QRPass.dto.request;

public record CreateTicketRequest(Long eventId,
                                  String name,
                                  String surname,
                                  String email) {
}
