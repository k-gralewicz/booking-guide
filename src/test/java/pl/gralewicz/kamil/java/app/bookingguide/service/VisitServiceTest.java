package pl.gralewicz.kamil.java.app.bookingguide.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.gralewicz.kamil.java.app.bookingguide.controller.model.Visit;
import pl.gralewicz.kamil.java.app.bookingguide.dao.repository.VisitRepository;
import pl.gralewicz.kamil.java.app.bookingguide.service.mapper.VisitMapper;

import static org.junit.jupiter.api.Assertions.*;

class VisitServiceTest {

    @Test
    void create() {
        // given
        VisitRepository visitRepository = new VisitRepository();
        VisitMapper visitMapper = new VisitMapper();
        VisitService visitService = new VisitService(visitRepository, visitMapper);

        // when
        Visit createdVisit = visitService.create(new Visit());

        // then
        Assertions.assertNotNull(createdVisit, "createdVisit is null");
    }
}