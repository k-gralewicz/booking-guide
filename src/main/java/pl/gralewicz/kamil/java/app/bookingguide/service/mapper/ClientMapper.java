package pl.gralewicz.kamil.java.app.bookingguide.service.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import pl.gralewicz.kamil.java.app.bookingguide.controller.model.Client;
import pl.gralewicz.kamil.java.app.bookingguide.dao.entity.ClientEntity;

import java.util.logging.Logger;

@Component
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

    public Client from(ClientEntity clientEntity) {
        LOGGER.info("from(" + clientEntity + ")");
        ModelMapper modelMapper = new ModelMapper();
        Client client = modelMapper.map(clientEntity, Client.class);
        LOGGER.info("from(...) = " + clientEntity);
        return client;
    }
}
