package pl.gralewicz.kamil.java.app.bookingguide.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.gralewicz.kamil.java.app.bookingguide.controller.model.Service;
import pl.gralewicz.kamil.java.app.bookingguide.controller.model.Shop;
import pl.gralewicz.kamil.java.app.bookingguide.dao.repository.ServiceRepository;
import pl.gralewicz.kamil.java.app.bookingguide.dao.repository.ShopRepository;
import pl.gralewicz.kamil.java.app.bookingguide.service.ServiceService;
import pl.gralewicz.kamil.java.app.bookingguide.service.ShopService;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class ServiceControllerIntegrationTest {

    @Autowired
    private ServiceRepository serviceRepository;
    @Autowired
    private ShopRepository shopRepository;
    @Autowired
    private ServiceService serviceService;
    @Autowired
    private ShopService shopService;

    @Test
    void delete() {
        // given
        Shop firstShop = new Shop();
        firstShop.setName("Salon 1");
        Shop createdFirstShop = shopService.create(firstShop);

        Shop secondShop = new Shop();
        secondShop.setName("Salon 2");
        Shop createdSecondShop = shopService.create(secondShop);

        Service firstService = new Service();
        firstService.setName("Masaż");
        Service createdFirstService = serviceService.create(firstService);
        createdFirstService.getShops().add(createdFirstShop);

        Service secondService = new Service();
        secondService.setName("Kwasy");
        Service createdSecondService = serviceService.create(secondService);
        createdSecondService.getShops().add(secondShop);

        Long selectedShopId = 1L;
        serviceService.delete(createdFirstService.getId(), selectedShopId);
        Shop readShop = shopService.read(createdFirstShop.getId());

        // when

        // then
        Assertions.assertAll(
                () -> Assertions.assertNotNull(readShop, "ReadShop is null"),
                () -> assertThrows(NoSuchElementException.class, () -> serviceService.read(createdFirstService.getId()))
        );
    }
}

// test integracyjny sprawdza czy usunięcie service nie usuwa razem shop.
// tworzymy dwa shopy każdy z oddzielnym jednym swoim service
// należy usunąć jeden service i sprawdzić czy powiązany z nim shop nie został usunięty.