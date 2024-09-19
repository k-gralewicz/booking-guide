package pl.gralewicz.kamil.java.app.bookingguide.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.gralewicz.kamil.java.app.bookingguide.controller.model.Client;
import pl.gralewicz.kamil.java.app.bookingguide.dao.repository.ClientRepository;
import pl.gralewicz.kamil.java.app.bookingguide.service.mapper.ClientMapper;

@SpringBootTest
class ClientServiceTest {

    @Autowired
    private ClientService clientService;
    private ClientRepository clientRepository;

    @Test
    void create() {
        // given
        ClientMapper clientMapper = new ClientMapper();
        ClientService clientService = new ClientService(clientRepository, clientMapper);

        // when
        Client createdClient = clientService.create(new Client("Anna", "Kowalska", "anna@wp.pl", "555444333", null));

        // then
        Assertions.assertNotNull(createdClient, "Created client is null");
    }
}