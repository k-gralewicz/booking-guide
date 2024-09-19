package pl.gralewicz.kamil.java.app.bookingguide.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.gralewicz.kamil.java.app.bookingguide.controller.model.Address;
import pl.gralewicz.kamil.java.app.bookingguide.controller.model.Client;

@SpringBootTest
class ClientServiceSpringTest {

    @Autowired
    private ClientService clientService;

    @Test
    void create() {
        // given
        Client client = new Client();

        // when
        Client createdClient = clientService.create(client);

        // then
        Assertions.assertAll(
                () -> Assertions.assertNotNull(createdClient, "Client is null"),
                () -> Assertions.assertNotNull(createdClient.getId(), "Client id is null")
        );
    }

    @Test
    void createWithAddress(){
        // given
        Address address = new Address();
        address.setStreet("Prosta");
        address.setCity("Warszawa");

        Client client = new Client();
        client.setAddress(address);

        // when
        Client createdClient = clientService.create(client);

        // then
        Assertions.assertAll(
                () -> Assertions.assertNotNull(createdClient, "Cliente is null"),
                () -> Assertions.assertNotNull(createdClient.getAddress(), "Addres is null")
        );
    }
}