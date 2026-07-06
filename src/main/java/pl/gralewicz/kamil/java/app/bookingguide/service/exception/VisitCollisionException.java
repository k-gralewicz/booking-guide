package pl.gralewicz.kamil.java.app.bookingguide.service.exception;

public class VisitCollisionException extends RuntimeException {
    public VisitCollisionException(String message) {
        super(message);
    }
}