package pl.gralewicz.kamil.java.app.bookingguide.service;

import pl.gralewicz.kamil.java.app.bookingguide.controller.model.DurationType;
import pl.gralewicz.kamil.java.app.bookingguide.controller.model.Service;
import pl.gralewicz.kamil.java.app.bookingguide.controller.model.Shop;
import pl.gralewicz.kamil.java.app.bookingguide.controller.model.Visit;
import pl.gralewicz.kamil.java.app.bookingguide.dao.entity.*;
import pl.gralewicz.kamil.java.app.bookingguide.dao.repository.ShopRepository;
import pl.gralewicz.kamil.java.app.bookingguide.dao.repository.UserRepository;
import pl.gralewicz.kamil.java.app.bookingguide.dao.repository.VisitRepository;
import pl.gralewicz.kamil.java.app.bookingguide.service.mapper.VisitMapper;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.logging.Logger;

@org.springframework.stereotype.Service // stereotyp informujący lub oznaczający klasę jako komponent springowy, który podlega DI i IoC.
public class VisitService {
    private static final Logger LOGGER = Logger.getLogger(VisitService.class.getName());

    private final VisitRepository visitRepository; // zależności
    private final VisitMapper visitMapper;
    private final ShopRepository shopRepository;
    private final UserRepository userRepository;
    private final VisitAvailabilityService visitAvailabilityService;

    public VisitService(VisitRepository visitRepository, VisitMapper visitMapper, ShopRepository shopRepository, UserRepository userRepository, VisitAvailabilityService visitAvailabilityService) { // wstrzykiwanie zależności
        this.visitRepository = visitRepository;
        this.visitMapper = visitMapper;
        this.shopRepository = shopRepository;
        this.userRepository = userRepository;
        this.visitAvailabilityService = visitAvailabilityService;
    }

    public List<Visit> list() {
        LOGGER.info("list()");
        List<VisitEntity> visitEntities = visitRepository.findAll();
        List<Visit> visits = visitMapper.fromEntities(visitEntities);
        LOGGER.info("list(...)= ");
        return visits;
    }

    public List<Visit> list(Long shopId) {
        LOGGER.info("list(" + shopId + ")");
        List<VisitEntity> visitEntities = visitRepository.findByShopId(shopId);
        List<Visit> visits = visitMapper.fromEntities(visitEntities);
        LOGGER.info("list(...)= " + visits);
        return visits;
    }

    public List<Visit> list(String username) {
        LOGGER.info("list(" + username + ")");
        UserEntity userByUsername = userRepository.findByUsername(username);
        ClientEntity client = userByUsername.getClient();
        Long clientId = client.getId();
        List<VisitEntity> visitEntities = visitRepository.findByClientId(clientId);
        List<Visit> visits = visitMapper.fromEntities(visitEntities);
        LOGGER.info("list(...)= ");
        return visits;
    }

    public Visit create(Visit visit) {
        LOGGER.info("create(" + visit + ")");

        if (visit == null) {
            throw new IllegalArgumentException("Visit cannot be null");
        }
        if (visit.getDueDate() == null) {
            throw new IllegalArgumentException("Visit due date cannot be null");
        }
        if (visit.getShop() == null || visit.getShop().getId() == null) {
            throw new IllegalArgumentException("Visit must be assigned to a valid shop with an ID");
        }
        if (visit.getService() == null || visit.getService().getId() == null) {
            throw new IllegalArgumentException("Visit must have a service with a valid ID");
        }

        Shop shop = visit.getShop();
        Service service = visit.getService();
        LocalDateTime requestedDateTime = visit.getDueDate();
        int duration = visit.getService().getDuration();
        DurationType durationType = visit.getService().getDurationType();

        Visit mappedVisit = visitAvailabilityService.book(shop, service, requestedDateTime, duration, durationType);

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

//    protected void availability(LocalDateTime date, int duration, DurationType durationType) {
//        LOGGER.info("availability(" + date + "," + duration + ", " + durationType + ")");
//        List<Visit> visits = list();
//        LOGGER.info("availability(...)= " + visits);
//    }
//}

    public boolean availability(Long shopId, LocalDateTime date, int duration, DurationType durationType) {
        LOGGER.info("availability(shopId=" + shopId + ", " + date + ", duration=" + duration + ", " + durationType + ")");

        if (shopId == null || date == null || durationType == null || duration <= 0) {
            LOGGER.warning("Niepoprawne lub brakujące parametry wejściowe w availability()");
            return false;
        }

        ShopEntity shopEntity = shopRepository.findById(shopId)
                .orElseThrow(() -> new java.util.NoSuchElementException("Nie znaleziono sklepu o ID: " + shopId));

        LocalDateTime proposedStart = date;
        LocalDateTime proposedEnd = proposedStart;

        switch (durationType) {
            case MINUTES:
                proposedEnd = proposedStart.plusMinutes(duration);
                break;
            case HOURS:
                proposedEnd = proposedStart.plusHours(duration);
                break;
            default:
                LOGGER.severe("Nieobsługiwany typ DurationType: " + durationType);
                return false;
        }

        if (shopEntity.getOpenFrom() != null && shopEntity.getOpenTo() != null) {
            LocalTime visitStartRaw = proposedStart.toLocalTime();
            LocalTime visitEndRaw = proposedEnd.toLocalTime();

            if (visitStartRaw.isBefore(shopEntity.getOpenFrom()) || visitEndRaw.isAfter(shopEntity.getOpenTo())) {
                LOGGER.info("availability(...)= false (Wizyta poza godzinami otwarcia sklepu)");
                return false;
            }
        }

        List<Visit> visits = list();

        for (Visit existingVisit : visits) {
            if (existingVisit.getDueDate() == null || existingVisit.getShop() == null || existingVisit.getService() == null) {
                continue;
            }

            if (existingVisit.getShop().getId().equals(shopId)) {

                LocalDateTime existingStart = existingVisit.getDueDate();
                LocalDateTime existingEnd = existingStart;

                // WYJAŚNIENIE: Wyciągamy obiekt Service jawnie określając jego typ.
                // Dzięki temu IntelliJ dokładnie wie, skąd brać metody getDuration() i getDurationType().
                pl.gralewicz.kamil.java.app.bookingguide.controller.model.Service existingService = existingVisit.getService();

                if (existingService != null && existingService.getDurationType() != null) {
                    switch (existingService.getDurationType()) {
                        case MINUTES:
                            existingEnd = existingStart.plusMinutes(existingService.getDuration());
                            break;
                        case HOURS:
                            existingEnd = existingStart.plusHours(existingService.getDuration());
                            break;
                    }
                }

                if (proposedStart.isBefore(existingEnd) && proposedEnd.isAfter(existingStart)) {
                    LOGGER.info("availability(...)= false (Kolizja z wizytą o ID: " + existingVisit.getId() + ")");
                    return false;
                }
            }
        }

        LOGGER.info("availability(...)= true (Termin jest wolny)");
        return true;
    }
}