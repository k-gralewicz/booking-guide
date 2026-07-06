package pl.gralewicz.kamil.java.app.bookingguide.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import pl.gralewicz.kamil.java.app.bookingguide.controller.model.*;
import pl.gralewicz.kamil.java.app.bookingguide.dao.entity.ClientEntity;
import pl.gralewicz.kamil.java.app.bookingguide.dao.entity.ShopEntity;
import pl.gralewicz.kamil.java.app.bookingguide.dao.entity.ServiceEntity;
import pl.gralewicz.kamil.java.app.bookingguide.dao.entity.VisitEntity;
import pl.gralewicz.kamil.java.app.bookingguide.dao.repository.ClientRepository;
import pl.gralewicz.kamil.java.app.bookingguide.dao.repository.ServiceRepository;
import pl.gralewicz.kamil.java.app.bookingguide.dao.repository.ShopRepository;
import pl.gralewicz.kamil.java.app.bookingguide.dao.repository.VisitRepository;
import pl.gralewicz.kamil.java.app.bookingguide.service.mapper.ServiceMapper;
import pl.gralewicz.kamil.java.app.bookingguide.service.mapper.ShopMapper;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;

@SpringBootTest
@Transactional
class VisitAvailabilityServiceTest {

    @Autowired
    private ShopRepository shopRepository;

    @Autowired
    private VisitRepository visitRepository;

    @Autowired
    private VisitAvailabilityService visitAvailabilityService;

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private ShopMapper shopMapper;

    @Autowired
    private ServiceMapper serviceMapper;

    @Autowired
    private ClientRepository clientRepository;

    private Long savedShopId;

    @BeforeEach
    void setUp() {
        ShopEntity shop = new ShopEntity();
        shop.setOpenFrom(LocalTime.of(8, 0));
        shop.setOpenTo(LocalTime.of(16, 0));

        ShopEntity savedShop = shopRepository.save(shop);
        this.savedShopId = savedShop.getId();
    }

    @Test
    void isAvailable() {
        //given
        LocalDateTime requestedTime = LocalDateTime.of(2026, Month.MAY, 25, 12, 0);

        //when
        Boolean result = visitAvailabilityService.isAvailable(savedShopId, requestedTime, 30, DurationType.MINUTES);

        //then
        Assertions.assertAll(
                () -> Assertions.assertNotNull(result, "result is null"),
                () -> Assertions.assertTrue(result, "result is not correct")
        );
    }

    @Test
    void isAvailableWithCollision() {
        //given
        LocalDateTime requestedTime = LocalDateTime.of(2026, Month.MAY, 25, 12, 0);

        ShopEntity shop = shopRepository.findById(savedShopId).orElseThrow();

        ServiceEntity existingService = new ServiceEntity();
        existingService.setDuration(30);
        existingService.setDurationType(DurationType.MINUTES);

        VisitEntity existingVisit = new VisitEntity();
        existingVisit.setShop(shop);
        existingVisit.setDueDate(LocalDateTime.of(2026, Month.MAY, 25, 11, 45));
        existingVisit.setService(existingService);

        visitRepository.save(existingVisit);

        //when
        Boolean result = visitAvailabilityService.isAvailable(savedShopId, requestedTime, 30, DurationType.MINUTES);

        //then
        Assertions.assertAll(
                () -> Assertions.assertNotNull(result, "result is null"),
                () -> Assertions.assertFalse(result, "result is not correct")
        );
    }

    @Test
    void isAvailableWithClosedShop() {
        //given
        LocalDateTime tooEarly = LocalDateTime.of(2026, Month.MAY, 25, 6, 0);

        //when
        Boolean result = visitAvailabilityService.isAvailable(savedShopId, tooEarly, 30, DurationType.MINUTES);

        //then
        Assertions.assertAll(
                () -> Assertions.assertNotNull(result, "result is null"),
                () -> Assertions.assertFalse(result, "result is not correct")
        );
    }

    @Transactional
    @Test
    void bookAvailableVisit(){
        //given
        LocalDateTime requestedTime = LocalDateTime.of(2026, Month.JULY, 25, 12, 0);

        ShopEntity shopEntity = shopRepository.findById(savedShopId).orElseThrow();
        shopEntity.setName("Salon");
        ShopEntity savedShopEntity = shopRepository.save(shopEntity);
        Shop mappedShop = shopMapper.from(savedShopEntity);

        ServiceEntity service = new ServiceEntity();
        service.setName("Zabieg");
        service.setDurationType(DurationType.MINUTES);
        service.setDuration(15);
        ServiceEntity saveServiceEntity = serviceRepository.save(service);
        Service mappedService = serviceMapper.from(saveServiceEntity);

        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setFirstName("Jan");
        clientEntity.setLastName("Kowalski");
        ClientEntity savedClientEntity = clientRepository.save(clientEntity);

        Client mappedClient = new Client();
        mappedClient.setId(savedClientEntity.getId());

        shopRepository.flush();
        serviceRepository.flush();
        clientRepository.flush();

        //when
        Visit bookedVisit = visitAvailabilityService.book(
                mappedShop,
                mappedService,
                mappedClient,
                requestedTime,
                15,
                DurationType.MINUTES
        );

        //then
        Assertions.assertAll(
                () -> Assertions.assertNotNull(bookedVisit, "bookedVisit is null"),
                () -> Assertions.assertNotNull(bookedVisit.getClient(), "Client in bookedVisit is null"),
                () -> Assertions.assertEquals(mappedClient.getId(), bookedVisit.getClient().getId(), "Client ID does not match"),
                () -> Assertions.assertEquals(mappedShop.getId(), bookedVisit.getShop().getId(), "Shop ID does not match"),
                () -> Assertions.assertEquals(requestedTime, bookedVisit.getDueDate(), "Due date does not match")
        );
    }
}