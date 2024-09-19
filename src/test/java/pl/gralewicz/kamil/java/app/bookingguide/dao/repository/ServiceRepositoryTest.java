package pl.gralewicz.kamil.java.app.bookingguide.dao.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.gralewicz.kamil.java.app.bookingguide.dao.entity.ServiceEntity;

import java.math.BigDecimal;

@SpringBootTest
class ServiceRepositoryTest {

    @Autowired

    private ServiceRepository serviceRepository;

    @Test
    void create() {
        // given
        ServiceEntity serviceEntity = new ServiceEntity();
        serviceEntity.setName("Masaż Kobido");
        serviceEntity.setPrice(BigDecimal.valueOf(250));

        // when
        ServiceEntity createdServiceEntity = serviceRepository.save(serviceEntity);

        // then
        Assertions.assertNotNull(createdServiceEntity, "CreatedServiceEntity is null");

    }
}