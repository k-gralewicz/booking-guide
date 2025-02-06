package pl.gralewicz.kamil.java.app.bookingguide.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.gralewicz.kamil.java.app.bookingguide.controller.model.Visit;
import pl.gralewicz.kamil.java.app.bookingguide.dao.repository.VisitRepository;
import pl.gralewicz.kamil.java.app.bookingguide.service.mapper.VisitMapper;
@SpringBootTest
class VisitServiceTest {

    @Autowired
    private VisitRepository visitRepository;

    @Test
    void create() {
        // given
        VisitMapper visitMapper = new VisitMapper();
        VisitService visitService = new VisitService(visitRepository, visitMapper);
        // zrobić poprawne DI i IOC w Springframework
        // when
        Visit createdVisit = visitService.create(new Visit());

        // then
        Assertions.assertAll(
                ()-> Assertions.assertNotNull(createdVisit, "cretedVisit is null"),
                ()-> Assertions.assertNotNull(createdVisit.getId(), "createdVisit id is null")
        );
    }
}