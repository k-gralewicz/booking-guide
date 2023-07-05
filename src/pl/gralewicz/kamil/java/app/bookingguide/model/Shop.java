package pl.gralewicz.kamil.java.app.bookingguide.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Shop {

    private String name;
    private String description;
    private String phoneNumber;

    private Address address;
    private List<Visit> visits = new ArrayList<>();

    public Shop(String name, String description, String phoneNumber, Address address) {
        this.name = name;
        this.description = description;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public void visitAvailable(LocalDateTime dueDate) {
        for (Visit visit : visits) {
            LocalDateTime visitDueDate = visit.getDueDate();
            boolean dueDateEquals = dueDate.isEqual(visitDueDate);
            if (dueDateEquals) {
                System.out.println("Termin zajęty przez wizytę " + visit);
            }else{
                System.out.println("Termin dostępny ");
            }
        }
    }
    // TODO: 14.06.2023 zmodyfikować metodę visitAvailable tak aby uwzględniała czas trwania wizyt

    public void addVisit(Visit visit){
    visits.add(visit);
    }

    public void allVisits(){
        System.out.println("Wszystkie wizyty " + visits);
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

    @Override
    public String toString() {
        return "Shop{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", address=" + address +
                '}';
    }
}
