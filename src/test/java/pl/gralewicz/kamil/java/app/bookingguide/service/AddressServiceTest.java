package pl.gralewicz.kamil.java.app.bookingguide.service;

import org.junit.jupiter.api.Test;
import pl.gralewicz.kamil.java.app.bookingguide.controller.model.Address;
import pl.gralewicz.kamil.java.app.bookingguide.dao.repository.AddressRepository;

class AddressServiceTest {

    @Test
    void create() {
        // given
        AddressRepository addressRepository = new AddressRepository();
        AddressService addressService = new AddressService(addressRepository);

        // when
        addressService.create(new Address());

        // then


    }
}