package pl.gralewicz.kamil.java.app.bookingguide.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;
import pl.gralewicz.kamil.java.app.bookingguide.controller.model.Address;

@SpringBootTest
class AddressControllerSpringTest {

    @Autowired
    private AddressController addressController;

    @Test
    void create() throws Exception {
        // given
        Address address = new Address();
        address.setStreet("Długa");

        // when
        String createdAddress = addressController.create(address);

        // then
        Assertions.assertAll(
                () -> Assert.notNull(createdAddress, "createdAddress is null")
        );
    }
}