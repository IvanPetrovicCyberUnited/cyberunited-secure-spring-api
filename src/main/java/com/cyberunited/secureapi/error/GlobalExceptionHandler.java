package com.cyberunited.secureapi.error;

import jakarta.servlet.http.HttpServletRequest;
import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex, HttpServletRequest req) {
        String msg = ex.getBindingResult().getAllErrors().stream()
                .map(err -> err instanceof FieldError fe ? fe.getField() + " " + fe.getDefaultMessage() : err.getDefaultMessage())
                .findFirst().orElse("Validation error");
        return build(HttpStatus.BAD_REQUEST, msg, req);
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErrorResponse> handleStatus(ResponseStatusException ex, HttpServletRequest req) {
        return build(ex.getStatusCode(), ex.getReason(), req);
    }

    private ResponseEntity<ErrorResponse> build(HttpStatusCode status, String message, HttpServletRequest req) {
        String cid = (String) req.getAttribute("correlationId");
        ErrorResponse err = new ErrorResponse(Instant.now(), req.getRequestURI(), String.valueOf(status.value()), message, cid);
        return new ResponseEntity<>(err, status);
    }
}
