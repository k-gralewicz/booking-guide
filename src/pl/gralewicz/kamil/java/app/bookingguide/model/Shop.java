package pl.gralewicz.kamil.java.app.bookingguide.model;

public class Shop {

    private String name;
    private String description;
    private String phoneNumber;

    private Address address;
//    private String street;
//    private String flatNumber;
//    private String postCode;
//    private String city;
//    private String country;

    public Shop() {
    }

    public Shop(String name, String description, String phoneNumber, Address address) {
        this.name = name;
        this.description = description;
        this.phoneNumber = phoneNumber;
        this.address = address;
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
