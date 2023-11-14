package pl.gralewicz.kamil.java.app.bookingguide.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.gralewicz.kamil.java.app.bookingguide.controller.model.Service;
import pl.gralewicz.kamil.java.app.bookingguide.dao.repository.ServiceRepository;
import pl.gralewicz.kamil.java.app.bookingguide.service.mapper.ServiceMapper;

import static org.junit.jupiter.api.Assertions.*;

class ServiceServiceTest {

    @Test
    void create() {
        /// given
        ServiceRepository serviceRepository = new ServiceRepository();
        ServiceMapper serviceMapper = new ServiceMapper();
        ServiceService serviceService = new ServiceService(serviceRepository, serviceMapper);

        // when
        Service createdService = serviceService.create(new Service());

        // then
        Assertions.assertNotNull(createdService, "createdService is null");
    }
}