package com.group1.QRPass.dto.response;

import java.sql.Timestamp;

public record GetEventResponse(Long id,
                               String title,
                               Timestamp time) {
}
