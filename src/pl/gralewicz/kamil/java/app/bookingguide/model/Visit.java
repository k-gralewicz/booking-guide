package pl.gralewicz.kamil.java.app.bookingguide.model;

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

    // TODO: dodać metody do obsługi wizyty (zaproponować w formie komentarza)

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
