package pl.gralewicz.kamil.java.app.bookingguide.service.mapper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.gralewicz.kamil.java.app.bookingguide.controller.model.Service;
import pl.gralewicz.kamil.java.app.bookingguide.dao.entity.ServiceEntity;

class ServiceMapperTest {

    @Test
    void from() {
        // given
        ServiceMapper serviceMapper = new ServiceMapper();

        Service service = new Service();
        service.setId(1L);
        service.setName("Mezoterapia igłowa");
        service.setDuration(30);

        // when
        ServiceEntity serviceEntity = serviceMapper.from(service);

        // then
        Assertions.assertAll(
                () -> Assertions.assertNotNull(serviceEntity, "serviceEntity is null"),
                () -> Assertions.assertNotNull(serviceEntity.getId(), "ServiceEntity ID is null"),
                () -> Assertions.assertNotNull(serviceEntity.getName(), "ServiceEntity Name is null")
        );
    }
}