package pl.gralewicz.kamil.java.app.bookingguide.controller.model;

import java.time.LocalDateTime;

public class Visit {

    private Client client;
    private Service service;
    private Shop shop;

    private LocalDateTime dueDate;

    public Visit(Client client, Service service, Shop shop, LocalDateTime dueDate) {
        this.client = client;
        this.service = service;
        this.shop = shop;
        this.dueDate = dueDate;
    }

    public boolean booking(LocalDateTime dueDate) {
        boolean isAvailable = shop.visitAvailable(dueDate);
        if (isAvailable) {
            shop.book(this);
        }
        return isAvailable;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    @Override
    public String toString() {
        return "Visit{" +
                "client=" + client +
                ", service=" + service +
                ", shop=" + shop +
                ", dueDate=" + dueDate +
                '}';
    }
}
