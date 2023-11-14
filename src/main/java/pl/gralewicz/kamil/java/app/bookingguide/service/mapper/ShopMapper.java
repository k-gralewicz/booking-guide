package pl.gralewicz.kamil.java.app.bookingguide.service.mapper;

import org.modelmapper.ModelMapper;
import pl.gralewicz.kamil.java.app.bookingguide.controller.model.Shop;
import pl.gralewicz.kamil.java.app.bookingguide.dao.entity.ShopEntity;

import java.util.logging.Logger;

public class ShopMapper {

    private static final Logger LOGGER = Logger.getLogger(ShopMapper.class.getName());

    public ShopEntity from(Shop shop) {
        LOGGER.info("from(" + shop + ")");
        ShopEntity shopEntity = new ShopEntity();
        shopEntity.setId(shop.getId());
        shopEntity.setName(shop.getName());
        LOGGER.info("from(...) = " + shopEntity);
        return shopEntity;
    }

    public Shop from(ShopEntity shopEntity) {
        LOGGER.info("from(" + shopEntity + ")");
        ModelMapper modelMapper = new ModelMapper();
        Shop shop = modelMapper.map(shopEntity, Shop.class);
        LOGGER.info("from(...) = " + shopEntity);
        return shop;
    }
}
