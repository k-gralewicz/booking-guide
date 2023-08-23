package pl.gralewicz.kamil.java.app.bookingguide.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.gralewicz.kamil.java.app.bookingguide.controller.model.Address;
import pl.gralewicz.kamil.java.app.bookingguide.dao.AddressDao;
import pl.gralewicz.kamil.java.app.bookingguide.service.AddressService;

class AddressControllerTest {

    @Test
    void givenDaoAndServiceAndController_whenCreateAddress_thenCreatedAddressNotNull() throws Exception {
        // given
        AddressDao addressDao = new AddressDao();
        AddressService addressService = new AddressService(addressDao);
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

        AddressDao addressDao = new AddressDao();
        AddressService addressService = new AddressService(addressDao);
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

        AddressDao addressDao = new AddressDao();
        AddressService addressService = new AddressService(addressDao);
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