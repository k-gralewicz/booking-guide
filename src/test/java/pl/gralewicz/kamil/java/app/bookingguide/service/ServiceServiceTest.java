package pl.gralewicz.kamil.java.app.bookingguide.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import pl.gralewicz.kamil.java.app.bookingguide.controller.model.Service;
import pl.gralewicz.kamil.java.app.bookingguide.controller.model.Shop;

@SpringBootTest
class ServiceServiceTest {

    @Autowired
    private ServiceService serviceService;

    @Autowired
    private ShopService shopService;

    @Transactional
    @Test
    void createWithShop() {
        // given
        Shop shop = new Shop();
        shop.setName("Salon");
        Shop createdShop = shopService.create(shop);

        Service service = new Service();
        service.setName("Masaż twarzy");

//        Set<Shop> shops = new HashSet<>();
//        shops.add(createdShop);
//        service.setShops(shops);

        // when
        Service createdService = serviceService.createWithShop(service, createdShop.getId());


        // then
        Assertions.assertAll(
                () -> Assertions.assertNotNull(createdService, "Service is null"),
                () -> Assertions.assertEquals(1, createdService.getShops().size(), "Service should have 1 shop"),
                () -> Assertions.assertTrue(createdService.getShops().contains(createdShop), "Service should contain the created shop"));
    }
}