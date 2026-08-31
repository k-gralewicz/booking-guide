package pl.gralewicz.kamil.java.app.bookingguide.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.gralewicz.kamil.java.app.bookingguide.api.VisitStatusType;
import pl.gralewicz.kamil.java.app.bookingguide.controller.model.Client;
import pl.gralewicz.kamil.java.app.bookingguide.controller.model.DurationType;
import pl.gralewicz.kamil.java.app.bookingguide.controller.model.Shop;
import pl.gralewicz.kamil.java.app.bookingguide.controller.model.Visit;
import pl.gralewicz.kamil.java.app.bookingguide.dao.entity.ClientEntity;
import pl.gralewicz.kamil.java.app.bookingguide.dao.entity.ServiceEntity;
import pl.gralewicz.kamil.java.app.bookingguide.dao.entity.ShopEntity;
import pl.gralewicz.kamil.java.app.bookingguide.dao.entity.UserEntity;
import pl.gralewicz.kamil.java.app.bookingguide.dao.entity.VisitEntity;
import pl.gralewicz.kamil.java.app.bookingguide.dao.entity.VisitStatusEntity;
import pl.gralewicz.kamil.java.app.bookingguide.dao.repository.ServiceRepository;
import pl.gralewicz.kamil.java.app.bookingguide.dao.repository.ShopRepository;
import pl.gralewicz.kamil.java.app.bookingguide.dao.repository.UserRepository;
import pl.gralewicz.kamil.java.app.bookingguide.dao.repository.VisitRepository;
import pl.gralewicz.kamil.java.app.bookingguide.service.exception.ShopClosedException;
import pl.gralewicz.kamil.java.app.bookingguide.service.exception.VisitCollisionException;
import pl.gralewicz.kamil.java.app.bookingguide.service.mapper.VisitMapper;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class VisitService {
    private static final Logger LOGGER = Logger.getLogger(VisitService.class.getName());

    private final VisitRepository visitRepository;
    private final VisitMapper visitMapper;
    private final ShopRepository shopRepository;
    private final UserRepository userRepository;
    private final VisitAvailabilityService visitAvailabilityService;
    private final ServiceRepository serviceRepository;

    public VisitService(VisitRepository visitRepository,
                        VisitMapper visitMapper,
                        ShopRepository shopRepository,
                        UserRepository userRepository,
                        VisitAvailabilityService visitAvailabilityService,
                        ServiceRepository serviceRepository) {
        this.visitRepository = visitRepository;
        this.visitMapper = visitMapper;
        this.shopRepository = shopRepository;
        this.userRepository = userRepository;
        this.visitAvailabilityService = visitAvailabilityService;
        this.serviceRepository = serviceRepository;
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
        if (userByUsername == null || userByUsername.getClient() == null) {
            return new ArrayList<>();
        }
        ClientEntity client = userByUsername.getClient();
        Long clientId = client.getId();
        List<VisitEntity> visitEntities = visitRepository.findByClientId(clientId);
        List<Visit> visits = visitMapper.fromEntities(visitEntities);
        LOGGER.info("list(...)= ");
        return visits;
    }

    public Visit create(Visit visit) throws VisitCollisionException, ShopClosedException {
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
        if (visit.getClient() == null || visit.getClient().getId() == null) {
            throw new IllegalArgumentException("Visit must have a client with a valid ID");
        }

        if (visit.getDueDate().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Nie można zarezerwować wizyty w przeszłości: " + visit.getDueDate());
        }

        Shop shop = visit.getShop();
        pl.gralewicz.kamil.java.app.bookingguide.controller.model.Service service = visit.getService();
        Client client = visit.getClient();
        LocalDateTime requestedDateTime = visit.getDueDate();
        int duration = service.getDuration();
        DurationType durationType = service.getDurationType();

        if (duration <= 0) {
            throw new IllegalArgumentException("Czas trwania usługi musi być większy niż 0");
        }
        if (durationType == null) {
            throw new IllegalArgumentException("Typ czasu trwania usługi nie może być nullem");
        }

        availability(shop.getId(), requestedDateTime, duration, durationType);

        Visit mappedVisit = visitAvailabilityService.book(shop, service, client, requestedDateTime, duration, durationType);

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

    @Transactional
    public Visit changeStatus(Long visitId, VisitStatusType newStatusType) {
        LOGGER.info("changeStatus(visitId=" + visitId + ", newStatus=" + newStatusType + ")");

        VisitEntity visitEntity = visitRepository.findById(visitId)
                .orElseThrow(() -> new NoSuchElementException("Nie znaleziono wizyty o ID: " + idForError(visitId)));

        LocalDateTime now = LocalDateTime.now();

        if (visitEntity.getStatusHistory() == null) {
            visitEntity.setStatusHistory(new ArrayList<>());
        }

        for (VisitStatusEntity statusEntity : visitEntity.getStatusHistory()) {
            if (statusEntity.getEndDate() == null) {
                statusEntity.setEndDate(now);
            }
        }

        VisitStatusEntity newStatusEntity = new VisitStatusEntity();
        newStatusEntity.setStatus(newStatusType);
        newStatusEntity.setStartDate(now);
        newStatusEntity.setVisit(visitEntity);

        visitEntity.getStatusHistory().add(newStatusEntity);

        visitEntity.setCurrentStatus(newStatusType);

        VisitEntity updatedEntity = visitRepository.save(visitEntity);
        Visit mappedVisit = visitMapper.from(updatedEntity);

        LOGGER.info("changeStatus(...) = " + mappedVisit);
        return mappedVisit;
    }

    private Long idForError(Long visitId) {
        return visitId;
    }

    public void availability(Long shopId, LocalDateTime proposedStart, int duration, DurationType durationType)
            throws ShopClosedException, VisitCollisionException {
        LOGGER.info("availability(shopId=" + shopId + ", " + proposedStart + ", duration=" + duration + ", " + durationType + ")");

        if (shopId == null || proposedStart == null || durationType == null || duration <= 0) {
            LOGGER.warning("Niepoprawne lub brakujące parametry wejściowe w availability()");
            throw new IllegalArgumentException("Niepoprawne parametry weryfikacji dostępności");
        }

        ShopEntity shopEntity = shopRepository.findById(shopId)
                .orElseThrow(() -> new NoSuchElementException("Nie znaleziono sklepu o ID: " + shopId));

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
                throw new IllegalArgumentException("Nieobsługiwany typ DurationType");
        }

        if (shopEntity.getOpenFrom() != null && shopEntity.getOpenTo() != null) {
            LocalTime visitStartRaw = proposedStart.toLocalTime();
            LocalTime visitEndRaw = proposedEnd.toLocalTime();

            if (visitStartRaw.isBefore(shopEntity.getOpenFrom()) || visitEndRaw.isAfter(shopEntity.getOpenTo())) {
                LOGGER.info("availability(...)= false (Wizyta poza godzinami otwarcia sklepu)");
                throw new ShopClosedException("Wizyta (" + visitStartRaw + " - " + visitEndRaw +
                        ") wykracza poza godziny otwarcia sklepu (" + shopEntity.getOpenFrom() + " - " + shopEntity.getOpenTo() + ")");
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
                    throw new VisitCollisionException("Wybrany termin nakłada się na istniejącą wizytę o ID: " + existingVisit.getId());
                }
            }
        }

        LOGGER.info("availability(...)= true (Termin jest wolny)");
    }

    /**
     * Oblicza cene wizyty na podstawie daty wizyty (DUE_DATE) oraz okresu obowiazywania uslugi (START_DATE - END_DATE).
     * Jeśli usługa z zakresem dat nie zostanie znaleziona, zwracana jest domyślna cena przypisana do wizyty/usługi.
     */
    public BigDecimal getVisitPriceForTimeRange(Long visitId) {
        LOGGER.info("getVisitPriceForTimeRange(" + visitId + ")");
        VisitEntity visitEntity = visitRepository.findById(visitId)
                .orElseThrow(() -> new NoSuchElementException("Nie znaleziono wizyty o ID: " + visitId));

        if (visitEntity.getService() == null || visitEntity.getService().getId() == null) {
            throw new IllegalArgumentException("Wizyta nie posiada przypisanej usługi.");
        }

        Long serviceId = visitEntity.getService().getId();
        LocalDateTime dueDate = visitEntity.getDueDate();
        LocalDate targetDate = (dueDate != null) ? dueDate.toLocalDate() : LocalDate.now();

        Optional<ServiceEntity> serviceForDate = serviceRepository.findServiceForDate(serviceId, targetDate);

        BigDecimal price = serviceForDate
                .map(ServiceEntity::getPrice)
                .orElseGet(() -> visitEntity.getService().getPrice());

        LOGGER.info("getVisitPriceForTimeRange(...) = " + price);
        return price;
    }
}