package pl.gralewicz.kamil.java.app.bookingguide.service;

import pl.gralewicz.kamil.java.app.bookingguide.controller.model.Shop;
import pl.gralewicz.kamil.java.app.bookingguide.dao.entity.ShopEntity;
import pl.gralewicz.kamil.java.app.bookingguide.dao.repository.ShopRepository;
import pl.gralewicz.kamil.java.app.bookingguide.service.mapper.ShopMapper;

import java.util.logging.Logger;

public class ShopService {
    private static final Logger LOGGER = Logger.getLogger(ShopService.class.getName());

    private ShopRepository shopRepository;
    private ShopMapper shopMapper;

    public ShopService(ShopRepository shopRepository, ShopMapper shopMapper) {
        this.shopRepository = shopRepository;
        this.shopMapper = shopMapper;
    }

    public Shop create(Shop shop) {
        LOGGER.info("create()");
        ShopEntity shopEntity = shopMapper.from(shop);
        ShopEntity createdShopEntity = shopRepository.create(shopEntity);
        Shop mappedShop = shopMapper.from(createdShopEntity);
        LOGGER.info("create(...)=" + mappedShop);
        return mappedShop;
    }
}
