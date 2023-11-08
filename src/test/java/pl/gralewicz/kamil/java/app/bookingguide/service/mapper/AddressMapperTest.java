package pl.gralewicz.kamil.java.app.bookingguide.service.mapper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.gralewicz.kamil.java.app.bookingguide.controller.model.Address;
import pl.gralewicz.kamil.java.app.bookingguide.dao.entity.AddressEntity;

class AddressMapperTest {

    @Test
    void fromModel() {
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

    @Test
    void fromEntity() {
        // given
        AddressMapper addressMapper = new AddressMapper();


        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setId(1L);
        addressEntity.setCity("Warszawa");
        addressEntity.setCountry("Polska");

        // when
        Address createdAddress = addressMapper.from(addressEntity);

        // then
        Assertions.assertAll(
                () -> Assertions.assertNotNull(createdAddress, "createdAddress is null"),
                () -> Assertions.assertNotNull(createdAddress.getId(), "createdAddress ID is null"),
                () -> Assertions.assertNotNull(createdAddress.getCity(), "createdAddress City is null"),
                () -> Assertions.assertNotNull(createdAddress.getCountry(), "createdAddress Country is null")
        );
    }
}