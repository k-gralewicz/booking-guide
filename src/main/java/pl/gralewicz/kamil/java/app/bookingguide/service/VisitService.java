package pl.gralewicz.kamil.java.app.bookingguide.service;

import org.springframework.stereotype.Service;
import pl.gralewicz.kamil.java.app.bookingguide.controller.model.Visit;
import pl.gralewicz.kamil.java.app.bookingguide.dao.entity.ShopEntity;
import pl.gralewicz.kamil.java.app.bookingguide.dao.entity.VisitEntity;
import pl.gralewicz.kamil.java.app.bookingguide.dao.repository.ShopRepository;
import pl.gralewicz.kamil.java.app.bookingguide.dao.repository.VisitRepository;
import pl.gralewicz.kamil.java.app.bookingguide.service.mapper.VisitMapper;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.logging.Logger;

@Service // stereotyp informujący lub oznaczający klasę jako komponent springowy, który podlega DI i IoC.
public class VisitService {
    private static final Logger LOGGER = Logger.getLogger(VisitService.class.getName());

    private final VisitRepository visitRepository; // zależności
    private final VisitMapper visitMapper;
    private final ShopRepository shopRepository;

    public VisitService(VisitRepository visitRepository, VisitMapper visitMapper, ShopRepository shopRepository) { // wstrzykiwanie zależności
        this.visitRepository = visitRepository;
        this.visitMapper = visitMapper;
        this.shopRepository = shopRepository;
    }

    public Visit create(Visit visit) {
        LOGGER.info("create(" + visit + ")");
        VisitEntity visitEntity = visitMapper.from(visit); // delegacja
        VisitEntity createdVisitEntity = visitRepository.save(visitEntity);
        Visit mappedVisit = visitMapper.from(createdVisitEntity);
        LOGGER.info("create(...) = " + mappedVisit);
        return mappedVisit;
    }

    public Visit createWithShop(Visit visit, Long shopId) {
        LOGGER.info("createWithShop(" + visit + ", " + shopId + ")");
        Optional<ShopEntity> optionalShopEntity = shopRepository.findById(shopId);
        ShopEntity shopEntity = optionalShopEntity.orElseThrow(() -> new NoSuchElementException("Nie znaleziono shop o ID: " + shopId));
        VisitEntity visitEntity = visitMapper.from(visit);
        visitEntity.setShop(shopEntity);
        VisitEntity createdVisitEntity = visitRepository.save(visitEntity);
        Visit mappedVisit = visitMapper.from(createdVisitEntity);
        LOGGER.info("createWithShop(...)= " + mappedVisit);
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

    public Visit update(Long id, Visit visit) {
        LOGGER.info("update(" + id + ", " + visit + ")");
        if (visit != null) {
            VisitEntity visitEntity = visitMapper.from(visit);
            VisitEntity createdVisitEntity = visitRepository.save(visitEntity);
            Visit mappedVisit = visitMapper.from(createdVisitEntity);
            LOGGER.info("update(...)= " + mappedVisit);
            return mappedVisit;
        }
        return null;
    }

    public void delete(Long id) {
        LOGGER.info("delete(" + id + ")");
        visitRepository.deleteById(id);
        LOGGER.info("delete(...)= ");
    }

    public List<Visit> list(Long shopId) {
        LOGGER.info("list(" + shopId + ")");
        List<VisitEntity> visitEntities = visitRepository.findByShopId(shopId);
        List<Visit> visits = visitMapper.fromEntities(visitEntities);
        LOGGER.info("list(...)= " + visits);
        return visits;
    }
}
