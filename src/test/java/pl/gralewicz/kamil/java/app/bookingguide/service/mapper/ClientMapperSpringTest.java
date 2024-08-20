package pl.gralewicz.kamil.java.app.bookingguide.service.mapper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;
import pl.gralewicz.kamil.java.app.bookingguide.controller.model.Client;
import pl.gralewicz.kamil.java.app.bookingguide.dao.entity.ClientEntity;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ClientMapperSpringTest {

    @Autowired
    private ClientMapper clientMapper;

    @Test
    void from() {
        // given
        Client client = new Client();

        // when
        ClientEntity clientEntity = clientMapper.from(client);

        // then
        Assertions.assertAll(
                () -> Assert.notNull(clientEntity, "client is null")
        );
    }
}