package pl.gralewicz.kamil.java.app.bookingguide.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.modelmapper.internal.util.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.gralewicz.kamil.java.app.bookingguide.controller.model.Client;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ClientControllerSpringTest {

    @Autowired
    private ClientController clientController;

    @Test
    void create() throws Exception {
        // given
        Client client = new Client();
        client.setLastName("Kowalska");

        // when
        Client createdClient = clientController.create(client);

        // then
        Assertions.assertAll(
                () -> Assert.notNull(createdClient, "createdClient is null")
        );
    }
}