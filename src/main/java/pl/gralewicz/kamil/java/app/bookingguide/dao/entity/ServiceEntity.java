package pl.gralewicz.kamil.java.app.bookingguide.dao.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import pl.gralewicz.kamil.java.app.bookingguide.controller.model.DurationType;

import java.math.BigDecimal;

@Entity
@Table(name = "SERVICES")
public class ServiceEntity {
    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String description;
    private BigDecimal price;
    private int duration;
    private DurationType durationType;

    public ServiceEntity() {

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

    public DurationType getDurationType() {
        return durationType;
    }

    public void setDurationType(DurationType durationType) {
        this.durationType = durationType;
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
                '}';
    }
}
