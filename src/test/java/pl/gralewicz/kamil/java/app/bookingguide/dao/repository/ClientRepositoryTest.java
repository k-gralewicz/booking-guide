package pl.gralewicz.kamil.java.app.bookingguide.dao.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.gralewicz.kamil.java.app.bookingguide.dao.entity.ClientEntity;

class ClientRepositoryTest {

    @Test
    void create() {
        // given
        ClientRepository clientRepository = new ClientRepository();
        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setFirstName("Jacek");
        clientEntity.setLastName("Kowalski");

        // when
        ClientEntity createdClientEntity = clientRepository.create(clientEntity);

        // then
        Assertions.assertNotNull(createdClientEntity, "CreatedClientEntity is null");
    }
}