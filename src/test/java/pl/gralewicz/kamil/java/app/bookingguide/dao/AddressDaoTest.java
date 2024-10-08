package pl.gralewicz.kamil.java.app.bookingguide.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.gralewicz.kamil.java.app.bookingguide.controller.model.Address;

class AddressDaoTest {

    @Test
    void create() {
        // given
        AddressDao addressDao = new AddressDao();
//        Address address = new Address("Konwaliowa", "12", "01-222", "Warszawa", "Polska");
        Address address = new Address();
        // when
        Address createdAddress = addressDao.create(address);
        // then
        Assertions.assertNotNull(createdAddress, "Created address is null");
    }
}