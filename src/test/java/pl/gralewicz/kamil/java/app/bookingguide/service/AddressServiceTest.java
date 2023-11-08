package pl.gralewicz.kamil.java.app.bookingguide.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.gralewicz.kamil.java.app.bookingguide.controller.model.Address;
import pl.gralewicz.kamil.java.app.bookingguide.dao.repository.AddressRepository;
import pl.gralewicz.kamil.java.app.bookingguide.service.mapper.AddressMapper;

class AddressServiceTest {

    @Test
    void create() {
        // given
        AddressRepository addressRepository = new AddressRepository();
        AddressMapper addressMapper = new AddressMapper();
        AddressService addressService = new AddressService(addressRepository, addressMapper);

        // when
        Address createdAddress = addressService.create(new Address());

        // then
        Assertions.assertNotNull(createdAddress, "createdAddress is null");
    }
}