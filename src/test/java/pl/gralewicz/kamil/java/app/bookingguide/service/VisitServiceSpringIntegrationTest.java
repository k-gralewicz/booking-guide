package pl.gralewicz.kamil.java.app.bookingguide.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.gralewicz.kamil.java.app.bookingguide.controller.model.Visit;

import java.util.List;

@SpringBootTest
class VisitServiceSpringIntegrationTest {

    @Autowired
    private VisitService visitService;

    @Test
    void list() {
        // given
        Visit visit = new Visit();

        // when
        Visit createdVisit = visitService.create(visit);
        List<Visit> visits = visitService.list();

        // then
        Assertions.assertAll(
                ()->Assertions.assertNotNull(visits, "Visits is null"),
                ()->Assertions.assertEquals(1, visits.size(), "Visits is not equals")
        );
    }
}