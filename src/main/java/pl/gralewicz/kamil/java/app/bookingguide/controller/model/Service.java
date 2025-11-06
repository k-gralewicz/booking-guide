package pl.gralewicz.kamil.java.app.bookingguide.controller.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class Service {

    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private int duration;
//    private String durationType; // TODO: stworzyć i użyć enum zamiast String
    private DurationType durationType;
    private Long statusId;

    private List<Status> statuses = new ArrayList<>();

    public Service() {
    }

    private Set<Shop> shops = new HashSet<>();

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

    public DurationType getDurationType() {
        return durationType;
    }

    public void setDurationType(DurationType durationType) {
        this.durationType = durationType;
    }

    public Long getStatusId() {
        return statusId;
    }

    public void setStatusId(Long statusId) {
        this.statusId = statusId;
    }

    public List<Status> getStatuses() {
        return statuses;
    }

    public void setStatuses(List<Status> statuses) {
        this.statuses = statuses;
    }

    public Set<Shop> getShops() {
        return shops;
    }

    public void setShops(Set<Shop> shops) {
        this.shops = shops;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Service service = (Service) o;
        return duration == service.duration && id.equals(service.id) && Objects.equals(name, service.name) && Objects.equals(description, service.description) && Objects.equals(price, service.price) && durationType == service.durationType && Objects.equals(shops, service.shops);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, price, duration, durationType, shops);
    }

    @Override
    public String toString() {
        return "Service{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", duration=" + duration +
                ", durationType=" + durationType +
                ", statusId=" + statusId +
                ", statuses=" + statuses +
                ", shops=" + shops +
                '}';
    }
}
