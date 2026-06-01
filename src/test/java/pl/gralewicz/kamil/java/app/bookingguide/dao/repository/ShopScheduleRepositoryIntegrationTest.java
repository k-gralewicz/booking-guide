package pl.gralewicz.kamil.java.app.bookingguide.dao.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import pl.gralewicz.kamil.java.app.bookingguide.dao.entity.ShopEntity;
import pl.gralewicz.kamil.java.app.bookingguide.dao.entity.ShopScheduleEntity;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ShopScheduleRepositoryIntegrationTest {

    @Autowired
    private ShopScheduleRepository shopScheduleRepository;

    @Test
    void create(){
        //given
        ShopEntity shopEntity = new ShopEntity();
        ShopScheduleEntity shopScheduleEntity = new ShopScheduleEntity();
        shopScheduleEntity.setShop(shopEntity);

        //when
        ShopScheduleEntity createdShopScheduleEntity = shopScheduleRepository.save(shopScheduleEntity);

        //then
        Assertions.assertAll(
                ()-> Assert.notNull(createdShopScheduleEntity, "CreatedShopScheduleEntity is null")
        );
    }
}