package pl.gralewicz.kamil.java.app.bookingguide.dao.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import pl.gralewicz.kamil.java.app.bookingguide.controller.model.DurationType;

import java.math.BigDecimal;
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
    private Integer duration;
    @Column(name = "DURATION_TYPE")
    private DurationType durationType;
    private String status;

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

    public Set<ShopEntity> getShops() {
        return shops;
    }

    public void setShops(Set<ShopEntity> shops) {
        this.shops = shops;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public DurationType getDurationType() {
        return durationType;
    }

    public void setDurationType(DurationType durationType) {
        this.durationType = durationType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        ServiceEntity that = (ServiceEntity) o;
//        return duration == that.duration && id.equals(that.id) && Objects.equals(name, that.name) && Objects.equals(description, that.description) && Objects.equals(price, that.price) && durationType == that.durationType;
//    }

//    @Override
//    public int hashCode() {
//        return Objects.hash(id, name, description, price, duration, durationType);
//    }

    @Override
    public String toString() {
        return "ServiceEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", duration=" + duration +
                ", durationType=" + durationType +
                ", status='" + status + '\'' +
                ", shops='" + shops + '\'' +
                '}';
    }
}
