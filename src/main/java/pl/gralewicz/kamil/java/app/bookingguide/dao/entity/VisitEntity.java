package pl.gralewicz.kamil.java.app.bookingguide.dao.entity;

import jakarta.persistence.*;
import pl.gralewicz.kamil.java.app.bookingguide.api.VisitStatusType;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "VISITS")
public class VisitEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    private ClientEntity client;

    // Poprawiono z @OneToOne na @ManyToOne oraz dodano brakujący @JoinColumn
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_id")
    private ServiceEntity service;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_id")
    private ShopEntity shop;

    private LocalDateTime dueDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "current_status")
    private VisitStatusType currentStatus;

    @OneToMany(mappedBy = "visit", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("startDate DESC")
    private List<VisitStatusEntity> statusHistory = new ArrayList<>();

    public VisitEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ClientEntity getClient() {
        return client;
    }

    public void setClient(ClientEntity client) {
        this.client = client;
    }

    public ServiceEntity getService() {
        return service;
    }

    public void setService(ServiceEntity service) {
        this.service = service;
    }

    public ShopEntity getShop() {
        return shop;
    }

    public void setShop(ShopEntity shop) {
        this.shop = shop;
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

    public List<VisitStatusEntity> getStatusHistory() {
        return statusHistory;
    }

    public void setStatusHistory(List<VisitStatusEntity> statusHistory) {
        this.statusHistory = statusHistory;
    }

    // Metoda pomocnicza do zmiany statusu i domykania poprzedniego
    public void addStatus(VisitStatusType newStatus) {
        LocalDateTime now = LocalDateTime.now();
        if (statusHistory != null && !statusHistory.isEmpty()) {
            VisitStatusEntity lastStatus = statusHistory.get(0);
            if (lastStatus.getEndDate() == null) {
                lastStatus.setEndDate(now);
            }
        } else if (statusHistory == null) {
            this.statusHistory = new ArrayList<>();
        }

        VisitStatusEntity statusEntity = new VisitStatusEntity(this, newStatus, now);
        this.statusHistory.add(0, statusEntity);
        this.currentStatus = newStatus;
    }

    @Override
    public String toString() {
        return "VisitEntity{" +
                "id=" + id +
                ", clientId=" + (client != null ? client.getId() : null) +
                ", serviceId=" + (service != null ? service.getId() : null) +
                ", shopId=" + (shop != null ? shop.getId() : null) +
                ", dueDate=" + dueDate +
                ", currentStatus=" + currentStatus +
                '}';
    }
}