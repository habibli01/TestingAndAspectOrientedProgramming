package org.example.sellingexchangeplatform.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(NOT_FOUND)
    public ErrorResponse handleNotFoundException(NotFoundException exception) {
        return ErrorResponse.builder()
                .message(exception.getMessage())
                .timestamp(LocalDateTime.now())
                .status(NOT_FOUND.value())
                .error("Not Found")
                .build();
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(BAD_REQUEST)
    public ErrorResponse handleBadRequestException(BadRequestException exception) {
        return ErrorResponse.builder()
                .message(exception.getMessage())
                .timestamp(LocalDateTime.now())
                .status(BAD_REQUEST.value())
                .error("Bad Request")
                .build();
    }

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(UNAUTHORIZED)
    public ErrorResponse handleUnauthorizedException(UnauthorizedException exception) {
        return ErrorResponse.builder()
                .message(exception.getMessage())
                .timestamp(LocalDateTime.now())
                .status(UNAUTHORIZED.value())
                .error("Unauthorized")
                .build();
    }

    @ExceptionHandler(ForbiddenException.class)
    @ResponseStatus(FORBIDDEN)
    public ErrorResponse handleForbiddenException(ForbiddenException exception) {
        return ErrorResponse.builder()
                .message(exception.getMessage())
                .timestamp(LocalDateTime.now())
                .status(FORBIDDEN.value())
                .error("Forbidden")
                .build();
    }

    @ExceptionHandler(ConflictException.class)
    @ResponseStatus(CONFLICT)
    public ErrorResponse handleConflictException(ConflictException exception) {
        return ErrorResponse.builder()
                .message(exception.getMessage())
                .timestamp(LocalDateTime.now())
                .status(CONFLICT.value())
                .error("Conflict")
                .build();
    }

    @ExceptionHandler(InternalServerException.class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    public ErrorResponse handleInternalServerException(InternalServerException exception) {
        return ErrorResponse.builder()
                .message(exception.getMessage())
                .timestamp(LocalDateTime.now())
                .status(INTERNAL_SERVER_ERROR.value())
                .error("Internal Server Error")
                .build();
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    public ErrorResponse handleGeneralException(Exception exception) {
        return ErrorResponse.builder()
                .message("Xəta baş verdi: " + exception.getMessage())
                .timestamp(LocalDateTime.now())
                .status(INTERNAL_SERVER_ERROR.value())
                .error("General Error")
                .build();
    }
}
