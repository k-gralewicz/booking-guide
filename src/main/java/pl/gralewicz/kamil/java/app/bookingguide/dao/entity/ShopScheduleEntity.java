package pl.gralewicz.kamil.java.app.bookingguide.dao.entity;

import jakarta.persistence.*;

import java.time.LocalTime;

@Entity
@Table(name = "SHOPS_SCHEDULE")
public class ShopScheduleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    @ManyToOne
    private ShopEntity shop;

    // private DayOfWeek dayOfWeek;
    private LocalTime startTime;
    private LocalTime endTime;
    private boolean isActive;

    public ShopScheduleEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ShopEntity getShop() {
        return shop;
    }

    public void setShop(ShopEntity shop) {
        this.shop = shop;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    @Override
    public String toString() {
        return "ShopScheduleEntity{" +
                "id=" + id +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", isActive=" + isActive +
                '}';
    }
}
