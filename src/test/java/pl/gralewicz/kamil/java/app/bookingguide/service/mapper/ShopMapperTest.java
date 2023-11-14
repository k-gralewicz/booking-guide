package pl.gralewicz.kamil.java.app.bookingguide.service.mapper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.gralewicz.kamil.java.app.bookingguide.controller.model.Shop;
import pl.gralewicz.kamil.java.app.bookingguide.dao.entity.ShopEntity;

class ShopMapperTest {

    @Test
    void fromModel() {
        // given
        ShopMapper shopMapper = new ShopMapper();

        Shop shop = new Shop();
        shop.setId(1L);
        shop.setName("FaceBeauty");
        shop.setPhoneNumber("606111222");

        // when
        ShopEntity shopEntity = shopMapper.from(shop);

        // then
        Assertions.assertAll(
                () -> Assertions.assertNotNull(shopEntity, "shopEntity is null"),
                () -> Assertions.assertNotNull(shopEntity.getId(), "ShopEntity Id is null"),
                () -> Assertions.assertNotNull(shopEntity.getName(), "ShopEntity Name is null")
        );
    }

    @Test
    void fromEntity() {
        /// given
        ShopMapper shopMapper = new ShopMapper();

        ShopEntity shopEntity = new ShopEntity();
        shopEntity.setId(1L);
        shopEntity.setName("Zakątek Piękna");
        shopEntity.setPhoneNumber("504254987");
        // when
        Shop createdShop = shopMapper.from(shopEntity);

        // then
        Assertions.assertAll(
                () -> Assertions.assertNotNull(createdShop, "createdShop is null"),
                () -> Assertions.assertNotNull(createdShop.getId(), "createdShop ID is null"),
                () -> Assertions.assertNotNull(createdShop.getName(), "createdShop Name is null"),
                () -> Assertions.assertNotNull(createdShop.getPhoneNumber(), "createdShop PhoneNumber is null")
        );
    }
}