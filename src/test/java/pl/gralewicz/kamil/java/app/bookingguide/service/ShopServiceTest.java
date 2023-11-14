package pl.gralewicz.kamil.java.app.bookingguide.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.gralewicz.kamil.java.app.bookingguide.controller.model.Shop;
import pl.gralewicz.kamil.java.app.bookingguide.dao.repository.ShopRepository;
import pl.gralewicz.kamil.java.app.bookingguide.service.mapper.ShopMapper;

class ShopServiceTest {

    @Test
    void create() {
        // given
        ShopRepository shopRepository = new ShopRepository();
        ShopMapper shopMapper = new ShopMapper();
        ShopService shopService = new ShopService(shopRepository, shopMapper);

        // when
        Shop createdShop = shopService.create(new Shop());

        // then
        Assertions.assertNotNull(createdShop, "createdShop is null");
    }
}