package pl.gralewicz.kamil.java.app.bookingguide.service;

import org.springframework.transaction.annotation.Transactional;
import pl.gralewicz.kamil.java.app.bookingguide.controller.model.*;
import pl.gralewicz.kamil.java.app.bookingguide.dao.entity.ClientEntity;
import pl.gralewicz.kamil.java.app.bookingguide.dao.entity.ServiceEntity;
import pl.gralewicz.kamil.java.app.bookingguide.dao.entity.ShopEntity;
import pl.gralewicz.kamil.java.app.bookingguide.dao.entity.VisitEntity;
import pl.gralewicz.kamil.java.app.bookingguide.dao.repository.ClientRepository;
import pl.gralewicz.kamil.java.app.bookingguide.dao.repository.ServiceRepository;
import pl.gralewicz.kamil.java.app.bookingguide.dao.repository.ShopRepository;
import pl.gralewicz.kamil.java.app.bookingguide.dao.repository.VisitRepository;
import pl.gralewicz.kamil.java.app.bookingguide.service.mapper.ServiceMapper;
import pl.gralewicz.kamil.java.app.bookingguide.service.mapper.ShopMapper;
import pl.gralewicz.kamil.java.app.bookingguide.service.mapper.VisitMapper;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.logging.Logger;

@org.springframework.stereotype.Service
@Transactional(readOnly = true)
public class VisitAvailabilityService {
    private static final Logger LOGGER = Logger.getLogger(VisitAvailabilityService.class.getName());

    private final ShopRepository shopRepository;
    private final VisitRepository visitRepository;
    private final ServiceRepository serviceRepository; // DODANO
    private final ClientRepository clientRepository;
    private final VisitMapper visitMapper;
    private final ShopMapper shopMapper;
    private final ServiceMapper serviceMapper;

    public VisitAvailabilityService(ShopRepository shopRepository, VisitRepository visitRepository, ServiceRepository serviceRepository, ClientRepository clientRepository, VisitMapper visitMapper, ShopMapper shopMapper, ServiceMapper serviceMapper) {
        this.shopRepository = shopRepository;
        this.visitRepository = visitRepository;
        this.serviceRepository = serviceRepository; // DODANO
        this.clientRepository = clientRepository;
        this.visitMapper = visitMapper;
        this.shopMapper = shopMapper;
        this.serviceMapper = serviceMapper;
    }

    public boolean isAvailable(Long shopId, LocalDateTime requestedDateTime, int duration, DurationType durationType) {
        LOGGER.info("isAvailable(shopId=" + shopId + ", date=" + requestedDateTime + ", duration=" + duration + ", type=" + durationType + ")");

        if (shopId == null || requestedDateTime == null || durationType == null || duration <= 0) {
            LOGGER.warning("Invalid input parameters in isAvailable()");
            return false;
        }

        ShopEntity shop = shopRepository.findById(shopId)
                .orElseThrow(() -> new NoSuchElementException("Shop not found with ID: " + shopId));

        LocalDateTime proposedStart = requestedDateTime;
        LocalDateTime proposedEnd = proposedStart;

        if (durationType == DurationType.MINUTES) {
            proposedEnd = proposedStart.plusMinutes(duration);
        } else if (durationType == DurationType.HOURS) {
            proposedEnd = proposedStart.plusHours(duration);
        }

        if (shop.getOpenFrom() != null && shop.getOpenTo() != null) {
            LocalTime visitStartTimeRaw = proposedStart.toLocalTime();
            LocalTime visitEndTimeRaw = proposedEnd.toLocalTime();

            if (visitStartTimeRaw.isBefore(shop.getOpenFrom()) || visitEndTimeRaw.isAfter(shop.getOpenTo())) {
                LOGGER.info("isAvailable(...)= false (Visit outside shop opening hours)");
                return false;
            }
        }

        List<VisitEntity> existingVisits = visitRepository.findByShopId(shopId);

        for (VisitEntity existingVisit : existingVisits) {
            if (existingVisit.getDueDate() == null || existingVisit.getService() == null) {
                continue;
            }

            LocalDateTime existingStartDateTime = existingVisit.getDueDate();
            LocalDateTime existingEndDateTime = existingStartDateTime;

            ServiceEntity existingService = existingVisit.getService();
            if (existingService.getDurationType() != null) {
                if (existingService.getDurationType().name().equals(DurationType.MINUTES.name())) {
                    existingEndDateTime = existingStartDateTime.plusMinutes(existingService.getDuration());
                } else if (existingService.getDurationType().name().equals(DurationType.HOURS.name())) {
                    existingEndDateTime = existingStartDateTime.plusHours(existingService.getDuration());
                }
            }

            if (proposedStart.isBefore(existingEndDateTime) && proposedEnd.isAfter(existingStartDateTime)) {
                LOGGER.info("isAvailable(...)= false (Collision with booking ID: " + existingVisit.getId() + ")");
                return false;
            }
        }

        LOGGER.info("isAvailable(...)= true (Term is completely free)");
        return true;
    }

    @Transactional
    public Visit book(Shop shop, Service service, Client client, // <-- DODANO KLIENTA
                      LocalDateTime requestedDateTime,
                      int duration,
                      DurationType durationType) {

        if (shop != null && service != null && client != null) {
            Long shopId = shop.getId();
            Long serviceId = service.getId();

            boolean available = isAvailable(shopId, requestedDateTime, duration, durationType);
            if (!available) {
                throw new IllegalStateException("Selected term is not available");
            }

            // Pobieramy zarządzane przez Hibernate encje z bazy danych
            ShopEntity shopEntity = shopRepository.findById(shopId).orElseThrow();
            ServiceEntity serviceEntity = serviceRepository.findById(serviceId).orElseThrow();
            ClientEntity clientEntity = clientRepository.findById(client.getId()).orElseThrow(); // <-- POBIERANIE ENCI KLIENTA

            VisitEntity visit = new VisitEntity();
            visit.setShop(shopEntity);
            visit.setService(serviceEntity);
            visit.setClient(clientEntity); // <-- PODPIĘCIE ENCI KLIENTA DO WIZYTY
            visit.setDueDate(requestedDateTime);

            VisitEntity savedVisit = visitRepository.save(visit);
            Visit mappedVisit = visitMapper.from(savedVisit);

            LOGGER.info("book(...)= " + mappedVisit);
            return mappedVisit;
        }

        return null;
    }
}