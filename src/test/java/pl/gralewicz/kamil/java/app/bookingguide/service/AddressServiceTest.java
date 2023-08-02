package pl.gralewicz.kamil.java.app.bookingguide.service;

import org.junit.jupiter.api.Test;
import pl.gralewicz.kamil.java.app.bookingguide.controller.model.Address;
import pl.gralewicz.kamil.java.app.bookingguide.dao.AddressDao;

import static org.junit.jupiter.api.Assertions.*;

class AddressServiceTest {

    @Test
    void create() {
        // given
        AddressDao addressDao = new AddressDao();
        AddressService addressService = new AddressService(addressDao);

        // when
        addressService.create(new Address());

        // then


    }
}