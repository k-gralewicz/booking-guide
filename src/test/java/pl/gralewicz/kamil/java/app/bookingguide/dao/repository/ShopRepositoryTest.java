package pl.gralewicz.kamil.java.app.bookingguide.dao.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.gralewicz.kamil.java.app.bookingguide.dao.entity.ShopEntity;

class ShopRepositoryTest {

    @Test
    void create() {
        // given
        ShopEntity shopEntity = new ShopEntity();
        shopEntity.setName("Zakątek piękna");
        shopEntity.setPhoneNumber("601589754");

        ShopRepository shopRepository = new ShopRepository();

        // when
        ShopEntity createdShopEntity = shopRepository.create(shopEntity);

        // then
        Assertions.assertNotNull(createdShopEntity, "createdShopEntity is null");
    }
}