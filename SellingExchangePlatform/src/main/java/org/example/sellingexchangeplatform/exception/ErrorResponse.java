package org.example.sellingexchangeplatform.exception;

import lombok.Builder;

import java.time.LocalDateTime;
@Builder
public record ErrorResponse(
        String message,
        LocalDateTime timestamp,
        int status,
        String error
) {
}
