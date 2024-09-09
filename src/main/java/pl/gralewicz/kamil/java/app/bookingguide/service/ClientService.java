package pl.gralewicz.kamil.java.app.bookingguide.service;

import org.springframework.stereotype.Service;
import pl.gralewicz.kamil.java.app.bookingguide.controller.model.Client;
import pl.gralewicz.kamil.java.app.bookingguide.dao.entity.ClientEntity;
import pl.gralewicz.kamil.java.app.bookingguide.dao.repository.ClientRepository;
import pl.gralewicz.kamil.java.app.bookingguide.service.mapper.ClientMapper;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class ClientService {
    private static final Logger LOGGER = Logger.getLogger(ClientService.class.getName());

    private ClientRepository clientRepository;
    private ClientMapper clientMapper;

    public ClientService(ClientRepository clientRepository, ClientMapper clientMapper) {
        this.clientRepository = clientRepository;
        this.clientMapper = clientMapper;
    }

    public List<Client> list() {
        LOGGER.info("list()");
        List<ClientEntity> clientEntities = clientRepository.findAll();
        List<Client> clients = clientMapper.fromEntities(clientEntities);
        LOGGER.info("list(...)= " + clients);
        return clients;
    }

    public Client create(Client client) {
        LOGGER.info("create()");
        ClientEntity clientEntity = clientMapper.from(client);
        ClientEntity createdClientEntity = clientRepository.save(clientEntity);
        Client mappedClient = clientMapper.from(createdClientEntity);
        LOGGER.info("create(...)=" + mappedClient);
        return mappedClient;
    }

    public Client read(Long id) {
        LOGGER.info("read(" + id + ")");
        Optional<ClientEntity> optionalClientEntity = clientRepository.findById(id);
        ClientEntity clientEntity = optionalClientEntity.orElseThrow();
        LOGGER.info("read(...)= ");
        return null;
    }

    public void delete(Long id) {
        LOGGER.info("delete(" + id + ")");
        clientRepository.deleteById(id);
        LOGGER.info("delete(...)= ");
    }
}
