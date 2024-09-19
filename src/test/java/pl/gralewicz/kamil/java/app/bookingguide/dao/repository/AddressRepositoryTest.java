package pl.gralewicz.kamil.java.app.bookingguide.dao.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.gralewicz.kamil.java.app.bookingguide.dao.entity.AddressEntity;

@SpringBootTest
class AddressRepositoryTest {

    @Autowired

    private AddressRepository addressRepository;

    @Test
    void create() {
        // given
        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setCity("Warszawa");
        addressEntity.setStreet("Strumykowa");

        // when
        AddressEntity createdAddressEntity = addressRepository.save(addressEntity);

        // then
        Assertions.assertNotNull(createdAddressEntity, "createdAddressEntity is null");
    }
}