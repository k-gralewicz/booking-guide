package pl.gralewicz.kamil.java.app.bookingguide.service;

import pl.gralewicz.kamil.java.app.bookingguide.controller.model.Client;
import pl.gralewicz.kamil.java.app.bookingguide.dao.entity.ClientEntity;
import pl.gralewicz.kamil.java.app.bookingguide.dao.repository.ClientRepository;
import pl.gralewicz.kamil.java.app.bookingguide.service.mapper.ClientMapper;

import java.util.logging.Logger;

public class ClientService {
    private static final Logger LOGGER = Logger.getLogger(ClientService.class.getName());

    private ClientRepository clientRepository;
    private ClientMapper clientMapper;

    public ClientService(ClientRepository clientRepository, ClientMapper clientMapper) {
        this.clientRepository = clientRepository;
        this.clientMapper = clientMapper;
    }

    public Client create(Client client) {
        LOGGER.info("create()");
        ClientEntity clientEntity = clientMapper.from(client);
        ClientEntity createdClientEntity = clientRepository.create(clientEntity);
        Client mappedClient = clientMapper.from(createdClientEntity);
        LOGGER.info("create(...)=" + mappedClient);
        return mappedClient;
    }
}
