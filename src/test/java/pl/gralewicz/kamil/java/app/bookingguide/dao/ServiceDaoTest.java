package pl.gralewicz.kamil.java.app.bookingguide.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.gralewicz.kamil.java.app.bookingguide.controller.model.DurationType;
import pl.gralewicz.kamil.java.app.bookingguide.controller.model.Service;

import java.math.BigDecimal;

class ServiceDaoTest {

    @Test
    void create() {
        // given
        ServiceDao serviceDao = new ServiceDao();
        Service service = new Service("Masaż twarzy", "Masaż całej twarzy", BigDecimal.valueOf(250), 30, DurationType.MINUTES);

        // when
        Service createdService = serviceDao.create(service);

        // then
        Assertions.assertNotNull(createdService, "Created service is null");
    }
}