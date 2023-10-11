package pl.gralewicz.kamil.java.app.bookingguide.service.mapper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.gralewicz.kamil.java.app.bookingguide.controller.model.Client;
import pl.gralewicz.kamil.java.app.bookingguide.dao.entity.ClientEntity;

class ClientMapperTest {

    @Test
    void from() {
        // given
        ClientMapper clientMapper = new ClientMapper();

        Client client = new Client();
        client.setId(1L);
        client.setFirstName("Anna");
        client.setLastName("Baranowska");

        // when
        ClientEntity clientEntity= clientMapper.from(client);

        // then
        Assertions.assertAll(
                () -> Assertions.assertNotNull(clientEntity, "clientEntity is null"),
                () -> Assertions.assertNotNull(clientEntity.getId(), "ClientEntity ID is null"),
                () -> Assertions.assertNotNull(clientEntity.getFirstName(), "ClientEntity FirstName is null")
        );
    }
}