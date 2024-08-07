package pl.gralewicz.kamil.java.app.bookingguide.service.mapper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.modelmapper.internal.util.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.gralewicz.kamil.java.app.bookingguide.controller.model.Address;
import pl.gralewicz.kamil.java.app.bookingguide.dao.entity.AddressEntity;

@SpringBootTest
class AddressMapperSpringTest {

    @Autowired
    private AddressMapper addressMapper;

    @Test
    void from() {
        // given
        Address adress = new Address();

        // when
        AddressEntity addressEntity = addressMapper.from(adress);

        // then
        Assertions.assertAll(
                () -> Assert.notNull(addressEntity, "address is null")
        );
    }
}