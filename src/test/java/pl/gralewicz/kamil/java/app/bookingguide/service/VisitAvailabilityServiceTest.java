package pl.gralewicz.kamil.java.app.bookingguide.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pl.gralewicz.kamil.java.app.bookingguide.controller.model.DurationType;
import pl.gralewicz.kamil.java.app.bookingguide.dao.entity.ShopEntity;
import pl.gralewicz.kamil.java.app.bookingguide.dao.entity.ServiceEntity;
import pl.gralewicz.kamil.java.app.bookingguide.dao.entity.VisitEntity;
import pl.gralewicz.kamil.java.app.bookingguide.dao.repository.ShopRepository;
import pl.gralewicz.kamil.java.app.bookingguide.dao.repository.VisitRepository;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

class VisitAvailabilityServiceTest {

    private ShopRepository shopRepository;
    private VisitRepository visitRepository;
    private VisitAvailabilityService visitAvailabilityService;

    @BeforeEach
    void setUp() {
        this.shopRepository = Mockito.mock(ShopRepository.class);
        this.visitRepository = Mockito.mock(VisitRepository.class);
        this.visitAvailabilityService = new VisitAvailabilityService(shopRepository, visitRepository);
    }

    @Test
    void isAvailable() {
        //given
        Long shopId = 1L;
        LocalDateTime requestedTime = LocalDateTime.of(2026, Month.MAY, 25, 12, 0);

        ShopEntity shop = new ShopEntity();
        shop.setId(shopId);
        shop.setOpenFrom(LocalTime.of(8, 0));
        shop.setOpenTo(LocalTime.of(16, 0));

        Mockito.when(shopRepository.findById(shopId)).thenReturn(Optional.of(shop));
        Mockito.when(visitRepository.findByShopId(shopId)).thenReturn(new ArrayList<>());

        //when
        Boolean result = visitAvailabilityService.isAvailable(shopId, requestedTime, 30, DurationType.MINUTES);

        //then
        Assertions.assertAll(
                () -> Assertions.assertNotNull(result, "result is null"),
                () -> Assertions.assertTrue(result, "result is not correct")
        );
    }

    @Test
    void isAvailableWithCollision() {
        //given
        Long shopId = 1L;
        LocalDateTime requestedTime = LocalDateTime.of(2026, Month.MAY, 25, 12, 0);

        ShopEntity shop = new ShopEntity();
        shop.setId(shopId);
        shop.setOpenFrom(LocalTime.of(8, 0));
        shop.setOpenTo(LocalTime.of(16, 0));

        VisitEntity existingVisit = new VisitEntity();
        existingVisit.setId(99L);
        existingVisit.setDueDate(LocalDateTime.of(2026, Month.MAY, 25, 11, 45));

        ServiceEntity existingService = new ServiceEntity();
        existingService.setDuration(30);
        existingService.setDurationType(pl.gralewicz.kamil.java.app.bookingguide.controller.model.DurationType.MINUTES);
        existingVisit.setService(existingService);

        Mockito.when(shopRepository.findById(shopId)).thenReturn(Optional.of(shop));
        Mockito.when(visitRepository.findByShopId(shopId)).thenReturn(List.of(existingVisit));

        //when
        Boolean result = visitAvailabilityService.isAvailable(shopId, requestedTime, 30, DurationType.MINUTES);

        //then
        Assertions.assertAll(
                () -> Assertions.assertNotNull(result, "result is null"),
                () -> Assertions.assertFalse(result, "result is not correct")
        );
    }

    @Test
    void isAvailableWithClosedShop() {
        //given
        Long shopId = 1L;
        LocalDateTime tooEarly = LocalDateTime.of(2026, Month.MAY, 25, 6, 0);

        ShopEntity shop = new ShopEntity();
        shop.setId(shopId);
        shop.setOpenFrom(LocalTime.of(8, 0));
        shop.setOpenTo(LocalTime.of(16, 0));

        Mockito.when(shopRepository.findById(shopId)).thenReturn(Optional.of(shop));

        //when
        Boolean result = visitAvailabilityService.isAvailable(shopId, tooEarly, 30, DurationType.MINUTES);

        //then
        Assertions.assertAll(
                () -> Assertions.assertNotNull(result, "result is null"),
                () -> Assertions.assertFalse(result, "result is not correct")
        );
    }
}