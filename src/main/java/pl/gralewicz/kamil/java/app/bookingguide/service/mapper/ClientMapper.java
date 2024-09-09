package pl.gralewicz.kamil.java.app.bookingguide.service.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import pl.gralewicz.kamil.java.app.bookingguide.controller.model.Client;
import pl.gralewicz.kamil.java.app.bookingguide.dao.entity.ClientEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Component
public class ClientMapper {

    private static final Logger LOGGER = Logger.getLogger(ClientMapper.class.getName());

    public List<Client> fromEntities(List<ClientEntity> clientEntities) {
        LOGGER.info("fromEntities()");

        List<Client> clients = new ArrayList<>();

        for (ClientEntity clientEntity : clientEntities) {
            Client client = from(clientEntity);
            clients.add(client);
        }

        LOGGER.info("fromEntities(...)= " + clients);
        return clients;
    }

    public ClientEntity from(Client client) {
        LOGGER.info("from(" + client + ")");
        ModelMapper modelMapper = new ModelMapper();
        ClientEntity clientEntity = modelMapper.map(client, ClientEntity.class);
        LOGGER.info("from(...) = " + clientEntity);
        return clientEntity;
    }

    public Client from(ClientEntity clientEntity) {
        LOGGER.info("from(" + clientEntity + ")");
        ModelMapper modelMapper = new ModelMapper();
        Client client = modelMapper.map(clientEntity, Client.class);
        LOGGER.info("from(...) = " + clientEntity);
        return client;
    }
}
// TODO: 21.08.2024 poprawić pierwszy from, dodać modelMapper (jak w drugim from), 