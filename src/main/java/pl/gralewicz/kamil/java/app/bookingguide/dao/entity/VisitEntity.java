package pl.gralewicz.kamil.java.app.bookingguide.dao.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "VISITS")
public class VisitEntity {
    @Id
    @GeneratedValue
    private Long id;

//    private ClientEntity client;
//    private ServiceEntity service;
//    private ShopEntity shop;
    private LocalDateTime dueDate;

    public VisitEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

//    public ClientEntity getClient() {
//        return client;
//    }
//
//    public void setClient(ClientEntity client) {
//        this.client = client;
//    }
//
//    public ServiceEntity getService() {
//        return service;
//    }
//
//    public void setService(ServiceEntity service) {
//        this.service = service;
//    }
//
//    public ShopEntity getShop() {
//        return shop;
//    }
//
//    public void setShop(ShopEntity shop) {
//        this.shop = shop;
//    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    @Override
    public String toString() {
        return "VisitEntity{" +
                "id=" + id +
//                ", client=" + client +
//                ", service=" + service +
//                ", shop=" + shop +
                ", dueDate=" + dueDate +
                '}';
    }
}
