package pl.gralewicz.kamil.java.app.bookingguide.service;

import org.springframework.stereotype.Service;
import pl.gralewicz.kamil.java.app.bookingguide.controller.model.Visit;
import pl.gralewicz.kamil.java.app.bookingguide.dao.entity.VisitEntity;
import pl.gralewicz.kamil.java.app.bookingguide.dao.repository.VisitRepository;
import pl.gralewicz.kamil.java.app.bookingguide.service.mapper.VisitMapper;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.logging.Logger;

@Service // stereotyp informujący lub oznaczający klasę jako komponent springowy, który podlega DI i IoC.
public class VisitService {
    private static final Logger LOGGER = Logger.getLogger(VisitService.class.getName());

    private VisitRepository visitRepository; // zależności
    private VisitMapper visitMapper;

    public VisitService(VisitRepository visitRepository, VisitMapper visitMapper) { // wstrzykiwanie zależności
        this.visitRepository = visitRepository;
        this.visitMapper = visitMapper;
    }

    public Visit create(Visit visit) {
        LOGGER.info("create()");
        VisitEntity visitEntity = visitMapper.from(visit); // delegacja
        VisitEntity createdVisitEntity = visitRepository.save(visitEntity);
        Visit mappedVisit = visitMapper.from(createdVisitEntity);
        LOGGER.info("create(...) = " + mappedVisit);
        return mappedVisit;
    }

    public List<Visit> list() {
        LOGGER.info("list()");
        List<VisitEntity> visitEntities = visitRepository.findAll();
        List<Visit> visits = visitMapper.fromEntities(visitEntities);
        LOGGER.info("list(...)= ");
        return visits;
    }

    public Visit read(Long id) {
        LOGGER.info("read(" + id + ")");
        Optional<VisitEntity> optionalVisitEntity = visitRepository.findById(id);
        VisitEntity visitEntity = optionalVisitEntity
                .orElseThrow(() -> new NoSuchElementException("Nie znaleziono wizyty o ID: " + id));
        Visit mappedVisit = visitMapper.from(visitEntity);
        LOGGER.info("read(...) = " + mappedVisit);
        return mappedVisit;
    }

    public void delete(Long id) {
        LOGGER.info("delete(" + id + ")");
        visitRepository.deleteById(id);
        LOGGER.info("delete(...)= ");
    }
}
