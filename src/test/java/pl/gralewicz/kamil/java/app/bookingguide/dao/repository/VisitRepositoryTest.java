package pl.gralewicz.kamil.java.app.bookingguide.dao.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.gralewicz.kamil.java.app.bookingguide.dao.entity.ShopEntity;
import pl.gralewicz.kamil.java.app.bookingguide.dao.entity.VisitEntity;

class VisitRepositoryTest {

    @Test
    void create() {
        // given
        ShopEntity shopEntity = new ShopEntity();
        VisitEntity visitEntity = new VisitEntity();
        visitEntity.setId(1L);
//        visitEntity.setShop(shopEntity);

        VisitRepository visitRepository = new VisitRepository();

        // when
        VisitEntity createdVisitEntity = visitRepository.create(visitEntity);

        // then
        Assertions.assertNotNull(createdVisitEntity, "createdVisitEntity is null");
    }
}