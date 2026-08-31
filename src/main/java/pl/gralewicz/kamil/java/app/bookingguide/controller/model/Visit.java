package pl.gralewicz.kamil.java.app.bookingguide.controller.model;

import org.springframework.format.annotation.DateTimeFormat;
import pl.gralewicz.kamil.java.app.bookingguide.api.VisitStatusType;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Visit {

    private Long id;
    private Long clientId;
    private Client client;
    private Long serviceId;
    private Service service;
    private Long shopId;
    private Shop shop;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime dueDate;

    private VisitStatusType currentStatus;
    private List<VisitStatus> statusHistory = new ArrayList<>();

    public Visit() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getClientId() {
        if (clientId == null && client != null) {
            return client.getId();
        }
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
        if (client != null && client.getId() != null) {
            this.clientId = client.getId();
        }
    }

    public Long getServiceId() {
        if (serviceId == null && service != null) {
            return service.getId();
        }
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
        if (service != null && service.getId() != null) {
            this.serviceId = service.getId();
        }
    }

    public Long getShopId() {
        if (shopId == null && shop != null) {
            return shop.getId();
        }
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
        if (shop != null && shop.getId() != null) {
            this.shopId = shop.getId();
        }
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public VisitStatusType getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(VisitStatusType currentStatus) {
        this.currentStatus = currentStatus;
    }

    public List<VisitStatus> getStatusHistory() {
        return statusHistory;
    }

    public void setStatusHistory(List<VisitStatus> statusHistory) {
        this.statusHistory = statusHistory;
    }

    @Override
    public String toString() {
        return "Visit{" +
                "id=" + id +
                ", clientId=" + getClientId() +
                ", serviceId=" + getServiceId() +
                ", shopId=" + getShopId() +
                ", dueDate=" + dueDate +
                ", currentStatus=" + currentStatus +
                ", statusHistory=" + statusHistory +
                '}';
    }
}