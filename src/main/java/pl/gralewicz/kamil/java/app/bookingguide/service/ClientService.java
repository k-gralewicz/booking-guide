package pl.gralewicz.kamil.java.app.bookingguide.service;

import pl.gralewicz.kamil.java.app.bookingguide.controller.model.Client;
import pl.gralewicz.kamil.java.app.bookingguide.dao.ClientDao;

import java.util.logging.Logger;

public class ClientService {
    private static final Logger LOGGER = Logger.getLogger(ClientService.class.getName());

    private ClientDao clientRepository;

    public ClientService(ClientDao clientRepository) {
        this.clientRepository = clientRepository;
    }

    public Client create(Client client) {
        LOGGER.info("create()");
        Client createdClient= clientRepository.create(client);
        LOGGER.info("create(...)=" + createdClient);
        return createdClient;
    }

}
