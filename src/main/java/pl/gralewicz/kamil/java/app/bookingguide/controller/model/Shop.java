package pl.gralewicz.kamil.java.app.bookingguide.controller.model;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.logging.Logger;

public class Shop {

    private static final Logger LOGGER = Logger.getLogger(Shop.class.getName());

    private Long id;
    private String name;
    private String description;
    private String phoneNumber;

    private Address address;
    private List<Visit> visits = new ArrayList<>();
    private Set<Service> services = new HashSet<>();

    private LocalTime openFrom;
    private LocalTime openTo;

    public Shop() {
    }

    public boolean visitAvailable(LocalDateTime dueDate) {
        LOGGER.info("visitAvailable(" + dueDate + ")");
        if (visits.size() > 0) {
            for (Visit visit : visits) {
            }
        } else {
            LOGGER.info("visitAvailable(...)=" + true);
            return true;
        }
        LOGGER.info("visitAvailable(...)=" + false);
        return false;

    }

    public Visit book(Visit visit) {
        LOGGER.info("book(" + visit + ")");
        if (visit != null) {

        }
        LOGGER.info("book(...)=" + null);
        return null;
    }

    public void allVisits() {
        System.out.println("Wszystkie wizyty " + visits);
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

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Set<Service> getServices() {
        return services;
    }

    public void setServices(Set<Service> services) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Shop shop = (Shop) o;
        return id.equals(shop.id) && Objects.equals(name, shop.name) && Objects.equals(description, shop.description) && Objects.equals(phoneNumber, shop.phoneNumber) && Objects.equals(address, shop.address) && Objects.equals(visits, shop.visits);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, phoneNumber, address, visits);
    }

    @Override
    public String toString() {
        return "Shop{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", address=" + address +
                ", openFrom=" + openFrom +
                ", openTo=" + openTo +
                '}';
    }
}
