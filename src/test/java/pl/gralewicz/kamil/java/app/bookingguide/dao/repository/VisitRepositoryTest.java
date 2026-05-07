package pl.gralewicz.kamil.java.app.bookingguide.dao.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.gralewicz.kamil.java.app.bookingguide.controller.model.Client;
import pl.gralewicz.kamil.java.app.bookingguide.controller.model.User;
import pl.gralewicz.kamil.java.app.bookingguide.controller.model.Visit;
import pl.gralewicz.kamil.java.app.bookingguide.dao.entity.ShopEntity;
import pl.gralewicz.kamil.java.app.bookingguide.dao.entity.VisitEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;

@SpringBootTest
class VisitRepositoryTest {

    @Autowired
    private VisitRepository visitRepository;

    @Autowired
    private ShopRepository shopRepository;

    @Test
    void create() {
        // given
        ShopEntity shopEntity = new ShopEntity();
        VisitEntity visitEntity = new VisitEntity();
        visitEntity.setId(1L);
//        visitEntity.setShop(shopEntity);

        // when
        VisitEntity createdVisitEntity = visitRepository.save(visitEntity);

        // then
        Assertions.assertNotNull(createdVisitEntity, "createdVisitEntity is null");
    }

    @Test
    void createVisitForShop() {
        // given
        ShopEntity shopEntity = new ShopEntity();
        shopEntity.setName("Kosmetyka");

        ShopEntity savedShopEntity = shopRepository.save(shopEntity);

        // when
        VisitEntity visitEntity = new VisitEntity();
        visitEntity.setShop(savedShopEntity);
        LocalDate dueDate = LocalDate.of(2023, Month.JULY, 14);
        visitEntity.setDueDate(dueDate);

        VisitEntity createdVisitEntity = visitRepository.save(visitEntity);
        // then
        Assertions.assertNotNull(savedShopEntity, "savedShopEntity is null");
        Assertions.assertNotNull(createdVisitEntity, "createdVisitEntity is null");
        Assertions.assertNotNull(createdVisitEntity.getShop(), "Shop is not assigned to the visit");
        Assertions.assertEquals(savedShopEntity.getName(), createdVisitEntity.getShop().getName(), "Shop name is not correctly assigned");
        Assertions.assertEquals(dueDate, createdVisitEntity.getDueDate(), "Due date is not set correctly");
    }
}