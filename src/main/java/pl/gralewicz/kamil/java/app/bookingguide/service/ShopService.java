package pl.gralewicz.kamil.java.app.bookingguide.service;

import org.springframework.stereotype.Service;
import pl.gralewicz.kamil.java.app.bookingguide.controller.model.Shop;
import pl.gralewicz.kamil.java.app.bookingguide.dao.entity.ShopEntity;
import pl.gralewicz.kamil.java.app.bookingguide.dao.repository.ShopRepository;
import pl.gralewicz.kamil.java.app.bookingguide.service.mapper.ShopMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Service
public class ShopService {
    private static final Logger LOGGER = Logger.getLogger(ShopService.class.getName());

    private ShopRepository shopRepository;
    private ShopMapper shopMapper;

    public ShopService(ShopRepository shopRepository, ShopMapper shopMapper) {
        this.shopRepository = shopRepository;
        this.shopMapper = shopMapper;
    }

    public List<Shop> list() {
        LOGGER.info("list()");
        List<Shop> shops = new ArrayList<>();
        Shop cosmeticShop = new Shop();
        cosmeticShop.setName("Rossmann");
        cosmeticShop.setId(1L);
        Shop groceryShop = new Shop();
        groceryShop.setName("Biedronka");
        groceryShop.setId(2L);
        shops.add(cosmeticShop);
        shops.add(groceryShop);
        LOGGER.info("list(...)= " + shops);
        return shops;
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
