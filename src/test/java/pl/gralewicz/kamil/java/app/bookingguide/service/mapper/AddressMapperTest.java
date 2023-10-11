package pl.gralewicz.kamil.java.app.bookingguide.service.mapper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.gralewicz.kamil.java.app.bookingguide.controller.model.Address;
import pl.gralewicz.kamil.java.app.bookingguide.dao.entity.AddressEntity;

class AddressMapperTest {

    @Test
    void from() {
        // given
        AddressMapper addressMapper = new AddressMapper();

        Address address = new Address();
        address.setId(1L);
        address.setCity("Warszawa");
        address.setStreet("Długa");

        // when
        AddressEntity addressEntity = addressMapper.from(address);

        // then
        Assertions.assertAll(
                () -> Assertions.assertNotNull(addressEntity, "addressEntity is null"),
                () -> Assertions.assertNotNull(addressEntity.getId(), "AddressEntity ID is null"),
                () -> Assertions.assertNotNull(addressEntity.getCity(), "AddressEntity City is null")
        );
    }
}