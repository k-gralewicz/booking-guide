package pl.gralewicz.kamil.java.app.bookingguide.service.mapper;

import pl.gralewicz.kamil.java.app.bookingguide.controller.model.Client;
import pl.gralewicz.kamil.java.app.bookingguide.dao.entity.ClientEntity;

import java.util.logging.Logger;

public class ClientMapper {

    private static final Logger LOGGER = Logger.getLogger(ClientMapper.class.getName());

    public ClientEntity from(Client client) {
        LOGGER.info("from(" + client + ")");
        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setId(client.getId());
        clientEntity.setFirstName(client.getFirstName());
        LOGGER.info("from(...) = " + clientEntity);
        return clientEntity;
    }
}
