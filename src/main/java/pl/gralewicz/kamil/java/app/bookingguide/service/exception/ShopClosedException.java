package pl.gralewicz.kamil.java.app.bookingguide.service.exception;

public class ShopClosedException extends RuntimeException {
    public ShopClosedException(String message) {
        super(message);
    }
}