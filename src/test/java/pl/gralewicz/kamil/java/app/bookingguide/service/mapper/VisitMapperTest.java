package pl.gralewicz.kamil.java.app.bookingguide.service.mapper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.gralewicz.kamil.java.app.bookingguide.controller.model.Client;
import pl.gralewicz.kamil.java.app.bookingguide.controller.model.Shop;
import pl.gralewicz.kamil.java.app.bookingguide.controller.model.Visit;
import pl.gralewicz.kamil.java.app.bookingguide.dao.entity.VisitEntity;

class VisitMapperTest {

    @Test
    void from() {
        // given
        VisitMapper visitMapper = new VisitMapper();

        Client client = new Client();
        client.setId(1L);
        client.setFirstName("Anna");
        client.setLastName("Baranowska");

        Shop shop = new Shop();
        shop.setId(1L);
        shop.setName("FaceBeauty");
        shop.setPhoneNumber("606111222");

        Visit visit = new Visit();
        visit.setId(1L);
        visit.setClient(client);
        visit.setShop(shop);

        // when
        VisitEntity visitEntity = visitMapper.from(visit);

        // then
        Assertions.assertAll(
                () -> Assertions.assertNotNull(visitEntity, "visitEntity is null"),
                () -> Assertions.assertNotNull(visitEntity.getId(), "VisitEntity ID is null"),
                () -> Assertions.assertNotNull(visitEntity.getClient(), "VisitEntity CLIENT is null"),
                () -> Assertions.assertNotNull(visitEntity.getShop(), "VisitEntity SHOP is null")
        );
    }
}