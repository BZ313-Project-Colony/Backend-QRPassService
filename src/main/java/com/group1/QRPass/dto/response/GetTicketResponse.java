package com.group1.QRPass.dto.response;

import java.sql.Timestamp;

public record GetTicketResponse(Long id,
                                String name,
                                String surname,
                                String email,
                                Timestamp registeredDate,
                                boolean isConfirmed) {
}
