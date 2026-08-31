package pl.gralewicz.kamil.java.app.bookingguide.dao.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import pl.gralewicz.kamil.java.app.bookingguide.api.ServiceStatusType;
import pl.gralewicz.kamil.java.app.bookingguide.controller.model.DurationType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static jakarta.persistence.CascadeType.MERGE;
import static jakarta.persistence.CascadeType.PERSIST;

@Entity
@Table(name = "SERVICES")
public class ServiceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    private String name;
    private String description;
    private BigDecimal price;
    private int duration;

    @Column(name = "DURATION_TYPE")
    private DurationType durationType;

    @Column(name = "SERVICE_STATUS_TYPE")
    private ServiceStatusType serviceStatusType;

    @Column(name = "START_DATE")
    private LocalDate startDate;

    @Column(name = "END_DATE")
    private LocalDate endDate;

    @ManyToMany(mappedBy = "services", cascade = {PERSIST, MERGE}, fetch = FetchType.EAGER)
    private Set<ShopEntity> shops = new HashSet<>();

    public ServiceEntity() {

    }

    public void addShop(ShopEntity shop) {
        shops.add(shop);
        shop.getServices().add(this);
    }

    public void delete(Long shopId) {
        shops.removeIf(shop -> shop.getId().equals(shopId));
    }

    public void deleteShop(ShopEntity shop) {
        shops.remove(shop);
        shop.getServices().remove(this);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Set<ShopEntity> getShops() {
        return shops;
    }

    public void setShops(Set<ShopEntity> shops) {
        this.shops = shops;
    }

    public DurationType getDurationType() {
        return durationType;
    }

    public void setDurationType(DurationType durationType) {
        this.durationType = durationType;
    }

    public ServiceStatusType getServiceStatusType() {
        return serviceStatusType;
    }

    public void setServiceStatusType(ServiceStatusType serviceStatusType) {
        this.serviceStatusType = serviceStatusType;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "ServiceEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", duration=" + duration +
                ", durationType=" + durationType +
                ", serviceStatusType=" + serviceStatusType +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", shops=" + shops +
                '}';
    }
}