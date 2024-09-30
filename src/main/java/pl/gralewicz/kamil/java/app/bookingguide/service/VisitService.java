package pl.gralewicz.kamil.java.app.bookingguide.service;

import org.springframework.stereotype.Service;
import pl.gralewicz.kamil.java.app.bookingguide.controller.model.Visit;
import pl.gralewicz.kamil.java.app.bookingguide.dao.entity.VisitEntity;
import pl.gralewicz.kamil.java.app.bookingguide.dao.repository.VisitRepository;
import pl.gralewicz.kamil.java.app.bookingguide.service.mapper.VisitMapper;

import java.util.List;
import java.util.logging.Logger;

@Service
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
        VisitEntity createdVisitEntity = visitRepository.save(visitEntity);
        Visit mappedVisit = visitMapper.from(createdVisitEntity);
        LOGGER.info("create(...) = " + mappedVisit);
        return mappedVisit;
    }

    public List<Visit> list(){
        LOGGER.info("list()");
        List<VisitEntity> visitEntities = visitRepository.findAll();
        List<Visit> visits = visitMapper.fromEntities(visitEntities);
        LOGGER.info("list(...)= ");
        return visits;
    }
}
