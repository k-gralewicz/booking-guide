package pl.gralewicz.kamil.java.app.bookingguide.dao.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.gralewicz.kamil.java.app.bookingguide.dao.entity.AddressEntity;

import static org.junit.jupiter.api.Assertions.*;

class AddressRepositoryTest {

    @Test
    void create() {
        // given
        AddressEntity addressEntity = new AddressEntity();
        AddressRepository addressRepository = new AddressRepository();

        // when
        AddressEntity createdAddressEntity = addressRepository.create(addressEntity);

        // then
        Assertions.assertNotNull(createdAddressEntity, "createdAddressEntity is null");
    }
}