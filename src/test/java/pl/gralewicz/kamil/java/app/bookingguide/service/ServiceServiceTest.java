package pl.gralewicz.kamil.java.app.bookingguide.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;
import pl.gralewicz.kamil.java.app.bookingguide.controller.model.Service;
import pl.gralewicz.kamil.java.app.bookingguide.dao.entity.ServiceEntity;
import pl.gralewicz.kamil.java.app.bookingguide.dao.repository.ServiceRepository;
import pl.gralewicz.kamil.java.app.bookingguide.service.mapper.ServiceMapper;

import java.util.List;

@SpringBootTest
class ServiceServiceTest {

    @Autowired
    private ServiceService serviceService;

    @Autowired
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

    @Test
    void list(){
        // given
        Service service = new Service();
        service.setName("Kwasy");

        // when
        Service createdService = serviceService.create(service);
        List<Service> services = serviceService.list();
        // przepisać do ServiceServiceSpringIntegrationTest (stworzyć)
        // then
        Assertions.assertAll(
                ()-> Assert.notNull(services, "Service is null"),
                ()->Assertions.assertEquals(1, services.size(), "Services is not equals")
        );

    }

    @Test
    void read() {
        // given
        ServiceEntity serviceEntity = new ServiceEntity();
        serviceEntity.setId(1L);
        serviceEntity.setName("Makijaż");
        serviceRepository.save(serviceEntity);
        // nie repository, a service, nie metoda save a create.
        // when
        Service service = serviceService.read(1L);

        // then
        Assertions.assertAll(
                ()->Assertions.assertNotNull(service, "Service is null"),
                ()->Assertions.assertEquals("Makijaż", service.getName(), "Service is not equals"),
                ()->Assertions.assertNotNull(service.getId(), "Service id is null")
        );
    }

}