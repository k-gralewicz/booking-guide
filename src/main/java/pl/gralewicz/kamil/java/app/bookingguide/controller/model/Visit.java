package pl.gralewicz.kamil.java.app.bookingguide.controller.model;

import java.time.LocalDateTime;

public class Visit {

    private Long id;
    private Client client;
    private Service service;
    private Shop shop;

    private LocalDateTime dueDate;

    public Visit(){
    }

    public boolean booking(LocalDateTime dueDate) {
        boolean isAvailable = shop.visitAvailable(dueDate);
        if (isAvailable) {
            shop.book(this);
        }
        return isAvailable;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
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
