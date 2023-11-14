package pl.gralewicz.kamil.java.app.bookingguide.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.gralewicz.kamil.java.app.bookingguide.controller.model.Client;
import pl.gralewicz.kamil.java.app.bookingguide.dao.repository.ClientRepository;
import pl.gralewicz.kamil.java.app.bookingguide.service.mapper.ClientMapper;

class ClientServiceTest {

    @Test
    void create() {
        // given
        ClientRepository clientRepository = new ClientRepository();
        ClientMapper clientMapper = new ClientMapper();
        ClientService clientService = new ClientService(clientRepository, clientMapper);

        // when
        Client createdClient = clientService.create(new Client("Anna", "Kowalska", "anna@wp.pl", "555444333", null));

        // then
        Assertions.assertNotNull(createdClient, "Created client is null");
    }
}