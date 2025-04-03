package pl.gralewicz.kamil.java.app.bookingguide.dao.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.gralewicz.kamil.java.app.bookingguide.dao.entity.ShopEntity;
@SpringBootTest
class ShopRepositoryTest {

    @Autowired
    private ShopRepository shopRepository;

    @Test
    void create() {
        // given
        ShopEntity shopEntity = new ShopEntity();

        // when
        ShopEntity createdShopEntity = shopRepository.save(shopEntity);

        // then
        Assertions.assertNotNull(createdShopEntity, "createdShopEntity is null");
    }
}