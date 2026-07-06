package pl.gralewicz.kamil.java.app.bookingguide.controller.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pl.gralewicz.kamil.java.app.bookingguide.service.exception.ShopClosedException;
import pl.gralewicz.kamil.java.app.bookingguide.service.exception.VisitCollisionException;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex) {
        return buildResponse(HttpStatus.BAD_REQUEST, "Złe żądanie", ex.getMessage());
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<Object> handleNoSuchElementException(NoSuchElementException ex) {
        return buildResponse(HttpStatus.NOT_FOUND, "Nie znaleziono zasobu", ex.getMessage());
    }

    @ExceptionHandler(VisitCollisionException.class)
    public ResponseEntity<Object> handleVisitCollisionException(VisitCollisionException ex) {
        return buildResponse(HttpStatus.CONFLICT, "Konflikt terminów", ex.getMessage());
    }

    @ExceptionHandler(ShopClosedException.class)
    public ResponseEntity<Object> handleShopClosedException(ShopClosedException ex) {
        return buildResponse(HttpStatus.UNPROCESSABLE_ENTITY, "Sklep zamknięty", ex.getMessage());
    }

    private ResponseEntity<Object> buildResponse(HttpStatus status, String error, String message) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", status.value());
        body.put("error", error);
        body.put("message", message);

        return new ResponseEntity<>(body, status);
    }
}