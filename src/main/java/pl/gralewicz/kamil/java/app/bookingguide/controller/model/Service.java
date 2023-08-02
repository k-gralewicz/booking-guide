package pl.gralewicz.kamil.java.app.bookingguide.controller.model;

import java.math.BigDecimal;

public class Service {

    private String name;
    private String description;
    private BigDecimal price;
    private int duration;
//    private String durationType; // TODO: stworzyć i użyć enum zamiast String
    private DurationType durationType;

    public Service() {
    }

    public Service(String name, String description, BigDecimal price, int duration, DurationType durationType) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.duration = duration;
        this.durationType = durationType;
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
        return "Service{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", duration=" + duration +
                ", durationType='" + durationType + '\'' +
                '}';
    }
}
