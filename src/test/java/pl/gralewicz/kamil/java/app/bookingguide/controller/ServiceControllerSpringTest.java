package pl.gralewicz.kamil.java.app.bookingguide.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ui.ModelMap;
import pl.gralewicz.kamil.java.app.bookingguide.controller.model.DurationType;
import pl.gralewicz.kamil.java.app.bookingguide.controller.model.Service;

import java.math.BigDecimal;

@SpringBootTest
class ServiceControllerSpringTest {

    @Autowired
    private ServiceController serviceController;
    //jak VisitControllerWebApplicationTest
    @Test
    void create() {
        // given
        Service service = new Service();
        service.setId(1L);
        service.setName("Henna");

        // when
        String createdService = serviceController.create(service);

        // then
        Assertions.assertAll(
                ()->Assertions.assertNotNull(createdService, "crestedService is null")
        );
    }

    @Test
    void read() {
        // given
        Service service = new Service();
        service.setId(1L);
        service.setName("Masaż");
        serviceController.create(service);

        // when
        String newService = serviceController.read(1L, "Masaż", "Masaż twarzy", BigDecimal.valueOf(150), 60, DurationType.MINUTES, new ModelMap());

        // then
        Assertions.assertAll(
                ()-> Assertions.assertNotNull(newService, "newService is null")
        );
    }
}