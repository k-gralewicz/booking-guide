package pl.gralewicz.kamil.java.app.bookingguide.dao.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.gralewicz.kamil.java.app.bookingguide.dao.entity.AddressEntity;
import pl.gralewicz.kamil.java.app.bookingguide.dao.entity.ClientEntity;

@SpringBootTest
class ClientRepositoryTest {

    @Autowired
    private ClientRepository clientRepository;

    @Test
    void create() {
        // given
        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setFirstName("Jacek");
        clientEntity.setLastName("Kowalski");

        // when
        ClientEntity createdClientEntity = clientRepository.save(clientEntity);

        // then
        Assertions.assertNotNull(createdClientEntity, "CreatedClientEntity is null");
    }

    @Test
    void createWithAddress() {
        // given

        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setCity("Warszawa");
        addressEntity.setStreet("Konwaliowa");

        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setFirstName("Jacek");
        clientEntity.setLastName("Kowalski");
        clientEntity.setAddress(addressEntity);

        // when
        ClientEntity createdClientEntity = clientRepository.save(clientEntity);

        // then
        Assertions.assertAll(
                () -> Assertions.assertNotNull(createdClientEntity, "CreatedClientEntity is null"),
                () -> Assertions.assertNotNull(createdClientEntity.getAddress().getId(), "CreatedClientEntity address id is null")
        );
    }
}