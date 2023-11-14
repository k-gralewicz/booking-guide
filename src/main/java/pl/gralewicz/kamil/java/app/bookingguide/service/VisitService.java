package pl.gralewicz.kamil.java.app.bookingguide.service;

import pl.gralewicz.kamil.java.app.bookingguide.controller.model.Visit;
import pl.gralewicz.kamil.java.app.bookingguide.dao.entity.VisitEntity;
import pl.gralewicz.kamil.java.app.bookingguide.dao.repository.VisitRepository;
import pl.gralewicz.kamil.java.app.bookingguide.service.mapper.VisitMapper;

import java.util.logging.Logger;

public class VisitService {
    private static final Logger LOGGER = Logger.getLogger(VisitService.class.getName());

    private VisitRepository visitRepository;
    private VisitMapper visitMapper;

    public VisitService(VisitRepository visitRepository, VisitMapper visitMapper) {
        this.visitRepository = visitRepository;
        this.visitMapper = visitMapper;
    }

    public Visit create(Visit visit) {
        LOGGER.info("create()");
        VisitEntity visitEntity = visitMapper.from(visit);
        VisitEntity createdVisitEntity = visitRepository.create(visitEntity);
        Visit mappedVisit = visitMapper.from(createdVisitEntity);
        LOGGER.info("create(...) = " + mappedVisit);
        return mappedVisit;
    }
}
