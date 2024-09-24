package pl.gralewicz.kamil.java.app.bookingguide.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.gralewicz.kamil.java.app.bookingguide.controller.model.Service;
import pl.gralewicz.kamil.java.app.bookingguide.dao.repository.ServiceRepository;
import pl.gralewicz.kamil.java.app.bookingguide.service.mapper.ServiceMapper;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ServiceServiceTest {

    @Autowired
    private ServiceService serviceService;
    private ServiceRepository serviceRepository;

    @Test
    void create() {
        /// given
        ServiceMapper serviceMapper = new ServiceMapper();
        ServiceService serviceService = new ServiceService(serviceRepository, serviceMapper);

        // when
        Service createdService = serviceService.create(new Service());

        // then
        Assertions.assertNotNull(createdService, "createdService is null");
    }
}