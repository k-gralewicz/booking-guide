package pl.gralewicz.kamil.java.app.bookingguide.dao.entity;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

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
    @GeneratedValue(strategy = GenerationType.TABLE)
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

    //
//    public List<Visit> getVisits() {
//        return visits;
//    }
//
//    public void setVisits(List<Visit> visits) {
//        this.visits = visits;
//    }


//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        ShopEntity that = (ShopEntity) o;
//        return id.equals(that.id) && Objects.equals(name, that.name) && Objects.equals(description, that.description) && Objects.equals(phoneNumber, that.phoneNumber) && Objects.equals(address, that.address) && Objects.equals(users, that.users) && Objects.equals(services, that.services);
//    }

//    @Override
//    public int hashCode() {
//        return Objects.hash(id, name, description, phoneNumber, address, users, services);
//    }

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
                '}';
    }
}
