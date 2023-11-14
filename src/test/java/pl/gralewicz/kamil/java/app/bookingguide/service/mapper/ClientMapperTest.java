package pl.gralewicz.kamil.java.app.bookingguide.service.mapper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.gralewicz.kamil.java.app.bookingguide.controller.model.Client;
import pl.gralewicz.kamil.java.app.bookingguide.dao.entity.ClientEntity;

class ClientMapperTest {

    @Test
    void fromModel() {
        // given
        ClientMapper clientMapper = new ClientMapper();

        Client client = new Client();
        client.setId(1L);
        client.setFirstName("Anna");
        client.setLastName("Baranowska");

        // when
        ClientEntity clientEntity = clientMapper.from(client);

        // then
        Assertions.assertAll(
                () -> Assertions.assertNotNull(clientEntity, "clientEntity is null"),
                () -> Assertions.assertNotNull(clientEntity.getId(), "ClientEntity ID is null"),
                () -> Assertions.assertNotNull(clientEntity.getFirstName(), "ClientEntity FirstName is null")
        );
    }

    @Test
    void fromEntity() {
        /// given
        ClientMapper clientMapper = new ClientMapper();

        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setId(1L);
        clientEntity.setFirstName("Emilia");
        clientEntity.setLastName("Nowakowska");

        // when
        Client createdClient = clientMapper.from(clientEntity);

        // then
        Assertions.assertAll(
                () -> Assertions.assertNotNull(createdClient, "createdClient is null"),
                () -> Assertions.assertNotNull(createdClient.getId(), "createdClient ID is null"),
                () -> Assertions.assertNotNull(createdClient.getFirstName(), "createdClient FirstName is null"),
                () -> Assertions.assertNotNull(createdClient.getLastName(), "createdClient LastName is null")
        );
    }
}