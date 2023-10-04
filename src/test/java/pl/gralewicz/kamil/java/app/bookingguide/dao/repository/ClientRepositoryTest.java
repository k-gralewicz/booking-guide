package pl.gralewicz.kamil.java.app.bookingguide.dao.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.gralewicz.kamil.java.app.bookingguide.dao.entity.AddressEntity;
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

    @Test
    void createWithAddress() {
        // given
        ClientRepository clientRepository = new ClientRepository();

        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setCity("Warszawa");
        addressEntity.setStreet("Konwaliowa");

        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setFirstName("Jacek");
        clientEntity.setLastName("Kowalski");
        clientEntity.setAddress(addressEntity);

        // when
        ClientEntity createdClientEntity = clientRepository.create(clientEntity);

        // then
        Assertions.assertAll(
                () -> Assertions.assertNotNull(createdClientEntity, "CreatedClientEntity is null"),
                () -> Assertions.assertNotNull(createdClientEntity.getAddress().getId(), "CreatedClientEntity address id is null")
        );
    }
}