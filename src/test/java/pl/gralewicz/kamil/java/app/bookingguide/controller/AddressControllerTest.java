package pl.gralewicz.kamil.java.app.bookingguide.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.gralewicz.kamil.java.app.bookingguide.controller.model.Address;
import pl.gralewicz.kamil.java.app.bookingguide.dao.AddressDao;
import pl.gralewicz.kamil.java.app.bookingguide.service.AddressService;

import static org.junit.jupiter.api.Assertions.*;

class AddressControllerTest {

    @Test
    void create() {
        // given
        AddressDao addressDao = new AddressDao();
        AddressService addressService = new AddressService(addressDao);
        AddressController addressController = new AddressController(addressService);

        // when
        Address createdAddress = addressController.create(new Address());

        // then
        Assertions.assertNotNull(createdAddress, "Created address is null");
    }
}