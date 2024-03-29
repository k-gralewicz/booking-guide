package pl.gralewicz.kamil.java.app.bookingguide.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.gralewicz.kamil.java.app.bookingguide.controller.model.Address;
import pl.gralewicz.kamil.java.app.bookingguide.dao.repository.AddressRepository;
import pl.gralewicz.kamil.java.app.bookingguide.service.AddressService;
import pl.gralewicz.kamil.java.app.bookingguide.service.mapper.AddressMapper;

class AddressControllerTest {

    @Test
    void givenDaoAndServiceAndController_whenCreateAddress_thenCreatedAddressNotNull() throws Exception {
        // given
        AddressRepository addressRespository = new AddressRepository();
        AddressMapper addressMapper = new AddressMapper();
        AddressService addressService = new AddressService(addressRespository, addressMapper);
        AddressController addressController = new AddressController(addressService);

        // when
        Address createdAddress = addressController.create(new Address());

        // then
        Assertions.assertNull(createdAddress, "Created address is not null");
    }

    @Test
    void givenAddressWithStreet_whenCreate_thenCreatedAddressNotNull() throws Exception {
        // given
        Address address = new Address();
        address.setStreet("Lazurowa");
        AddressMapper addressMapper = new AddressMapper();

        AddressRepository addressRepository = new AddressRepository();
        AddressService addressService = new AddressService(addressRepository, addressMapper);
        AddressController addressController = new AddressController(addressService);

        // when

//        try {
        Address createdAddress = addressController.create(address);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        // then
        Assertions.assertNull(createdAddress, "Created address is not null");
    }

    @Test
    void givenAddressWithoutStreet_whenCreate_thenCreatedAddressNotNull() throws Exception {
        // given
        Address address = new Address();
//        address.setStreet("Lazurowa");
        AddressMapper addressMapper = new AddressMapper();

        AddressRepository addressRepository = new AddressRepository();
        AddressService addressService = new AddressService(addressRepository, addressMapper);
        AddressController addressController = new AddressController(addressService);

        // when

//        try {
//        Address createdAddress = addressController.create(address);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        // then
//        Assertions.assertNull(createdAddress, "Created address is not null");
        Assertions.assertThrows(
                Exception.class,
                () -> addressController.create(address)
        );
    }
}