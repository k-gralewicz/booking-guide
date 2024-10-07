package pl.gralewicz.kamil.java.app.bookingguide.dao.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.gralewicz.kamil.java.app.bookingguide.dao.entity.ShopEntity;
import pl.gralewicz.kamil.java.app.bookingguide.dao.entity.VisitEntity;

@SpringBootTest
class VisitRepositoryTest {

    @Autowired
    private VisitRepository visitRepository;

    @Test
    void create() {
        // given
        ShopEntity shopEntity = new ShopEntity();
        VisitEntity visitEntity = new VisitEntity();
        visitEntity.setId(1L);
//        visitEntity.setShop(shopEntity);

        // when
        VisitEntity createdVisitEntity = visitRepository.save(visitEntity);

        // then
        Assertions.assertNotNull(createdVisitEntity, "createdVisitEntity is null");
    }
}