package pl.gralewicz.kamil.java.app.bookingguide.service.mapper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.gralewicz.kamil.java.app.bookingguide.controller.model.Visit;
import pl.gralewicz.kamil.java.app.bookingguide.dao.entity.VisitEntity;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class VisitMapperSpringTest {

    @Autowired
    private VisitMapper visitMapper;

    @Test
    void fromEntities() {
        // given
        List<VisitEntity> visitEntities = new ArrayList<>();
        visitEntities.add(new VisitEntity());
        // when
        List<Visit> visits = visitMapper.fromEntities(visitEntities);

        // then
        Assertions.assertAll(
                ()->Assertions.assertNotNull(visits, "Visits is null"),
                ()-> Assertions.assertEquals(1, visits.size(), "Visits is not equals")
        );
    }
}