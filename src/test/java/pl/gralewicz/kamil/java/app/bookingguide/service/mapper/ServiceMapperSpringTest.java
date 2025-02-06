package pl.gralewicz.kamil.java.app.bookingguide.service.mapper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.gralewicz.kamil.java.app.bookingguide.controller.model.Service;
import pl.gralewicz.kamil.java.app.bookingguide.dao.entity.ServiceEntity;

@SpringBootTest
class ServiceMapperSpringTest {

    @Autowired
    private ServiceMapper serviceMapper;
    // przepisać jako zwykły (niespringowy do ServiceMapperTest)
    @Test
    void from() {
        // given
        Service service = new Service();
        service.setName("Kawitacja");
        service.setId(1L);

        // when
        ServiceEntity serviceEntity = serviceMapper.from(service);

        // then
        Assertions.assertAll(
                ()->Assertions.assertNotNull(serviceEntity, "serviceEntity is null"),
                ()->Assertions.assertNotNull(serviceEntity.getName(), "serviceEntity name is null"),
                ()->Assertions.assertNotNull(serviceEntity.getId(), "serviceEntity id is null")
        );
    }
}