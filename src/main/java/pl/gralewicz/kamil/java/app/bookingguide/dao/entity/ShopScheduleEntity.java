package pl.gralewicz.kamil.java.app.bookingguide.dao.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "SHOPS_SCHEDULE")
public class ShopScheduleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    @ManyToOne
    private ShopEntity shop;

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

    @Override
    public String toString() {
        return "ShopScheduleEntity{" +
                "id=" + id +
                ", shop=" + shop +
                '}';
    }
}
