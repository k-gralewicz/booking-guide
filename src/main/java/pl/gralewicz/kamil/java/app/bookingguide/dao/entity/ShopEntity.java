package pl.gralewicz.kamil.java.app.bookingguide.dao.entity;


import jakarta.persistence.*;

import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static jakarta.persistence.CascadeType.MERGE;
import static jakarta.persistence.CascadeType.PERSIST;

@Entity
@Table(name = "SHOPS")
public class ShopEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String name;
    private String description;
    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;
    @OneToOne(cascade = CascadeType.ALL)
    private AddressEntity address;
//    private List<Visit> visits = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER)
    private List<UserEntity> users = new ArrayList<>();

    @ManyToMany(cascade = {PERSIST, MERGE}, fetch = FetchType.EAGER)
    private Set<ServiceEntity> services = new HashSet<>();

    private LocalTime openFrom;
    private LocalTime openTo;

    @OneToMany(mappedBy = "shop")
    private List<ShopScheduleEntity> shopSchedules = new ArrayList<>();

    public ShopEntity() {

    }

    public void addUser(UserEntity user){
        users.add(user);
        user.getShops().add(this);
    }

    public void addService(ServiceEntity service){
        services.add(service);
        service.getShops().add(this);
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public AddressEntity getAddress() {
        return address;
    }

    public void setAddress(AddressEntity address) {
        this.address = address;
    }

    public List<UserEntity> getUsers() {
        return users;
    }

    public void setUsers(List<UserEntity> users) {
        this.users = users;
    }

    public Set<ServiceEntity> getServices() {
        return services;
    }

    public void setServices(Set<ServiceEntity> services) {
        this.services = services;
    }

    public LocalTime getOpenFrom() {
        return openFrom;
    }

    public void setOpenFrom(LocalTime openFrom) {
        this.openFrom = openFrom;
    }

    public LocalTime getOpenTo() {
        return openTo;
    }

    public void setOpenTo(LocalTime openTo) {
        this.openTo = openTo;
    }

    public List<ShopScheduleEntity> getShopSchedules() {
        return shopSchedules;
    }

    public void setShopSchedules(List<ShopScheduleEntity> shopSchedules) {
        this.shopSchedules = shopSchedules;
    }

    @Override
    public String toString() {
        return "ShopEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", address=" + address +
                ", users=" + users +
//                ", services=" + services +
                ", openFrom=" + openFrom +
                ", openTo=" + openTo +
                ", shopSchedules=" + shopSchedules +
                '}';
    }
}
