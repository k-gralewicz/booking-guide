package pl.gralewicz.kamil.java.app.bookingguide.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.gralewicz.kamil.java.app.bookingguide.controller.model.Shop;
import pl.gralewicz.kamil.java.app.bookingguide.dao.repository.ShopRepository;
import pl.gralewicz.kamil.java.app.bookingguide.service.mapper.ShopMapper;

@SpringBootTest
class ShopServiceTest {

    @Autowired
    private ShopService shopService;

    @Autowired
    private ShopRepository shopRepository;

    @Test
    void create() {
        // given
        ShopMapper shopMapper = new ShopMapper();
        ShopService shopService = new ShopService(shopRepository, shopMapper);

        // when
        Shop createdShop = shopService.create(new Shop());

        // then
        Assertions.assertNotNull(createdShop, "createdShop is null");
    }
}