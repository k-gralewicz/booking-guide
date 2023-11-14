package pl.gralewicz.kamil.java.app.bookingguide.dao.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.gralewicz.kamil.java.app.bookingguide.dao.entity.ServiceEntity;

import java.math.BigDecimal;

class ServiceRepositoryTest {

    @Test
    void create() {
        // given
        ServiceRepository serviceRepository = new ServiceRepository();
        ServiceEntity serviceEntity = new ServiceEntity();
        serviceEntity.setName("Masaż Kobido");
        serviceEntity.setPrice(BigDecimal.valueOf(250));

        // when
        ServiceEntity createdServiceEntity = serviceRepository.create(serviceEntity);

        // then
        Assertions.assertNotNull(createdServiceEntity, "CreatedServiceEntity is null");

    }
}