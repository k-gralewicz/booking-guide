package pl.gralewicz.kamil.java.app.bookingguide.dao.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "ADDRESSES")
public class AddressEntity {
    @Id
    @GeneratedValue
    private Long id;

    private String street;
    @Column(name = "FLAT_NUMBER")
    private String flatNumber;
    @Column(name = "POST_CODE")
    private String postCode;
    private String city;
    private String country;

    public AddressEntity() {
    }
}