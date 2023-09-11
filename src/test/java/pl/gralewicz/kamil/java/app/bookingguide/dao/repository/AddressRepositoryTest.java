package pl.gralewicz.kamil.java.app.bookingguide.dao.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.gralewicz.kamil.java.app.bookingguide.dao.entity.AddressEntity;

class AddressRepositoryTest {

    @Test
    void create() {
        // given
        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setCity("Warszawa");
        addressEntity.setStreet("Strumykowa");

        AddressRepository addressRepository = new AddressRepository();

        // when
        AddressEntity createdAddressEntity = addressRepository.create(addressEntity);

        // then
        Assertions.assertNotNull(createdAddressEntity, "createdAddressEntity is null");
    }
}