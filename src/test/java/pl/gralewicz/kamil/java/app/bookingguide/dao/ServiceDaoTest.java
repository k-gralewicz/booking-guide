package pl.gralewicz.kamil.java.app.bookingguide.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.gralewicz.kamil.java.app.bookingguide.controller.model.Service;

class ServiceDaoTest {

    @Test
    void create() {
        // given
        ServiceDao serviceDao = new ServiceDao();
        Service service = new Service();

        // when
        Service createdService = serviceDao.create(service);

        // then
        Assertions.assertNotNull(createdService, "Created service is null");
    }
}