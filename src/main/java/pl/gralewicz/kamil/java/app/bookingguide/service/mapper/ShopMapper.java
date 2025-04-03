package pl.gralewicz.kamil.java.app.bookingguide.service.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import pl.gralewicz.kamil.java.app.bookingguide.controller.model.Shop;
import pl.gralewicz.kamil.java.app.bookingguide.dao.entity.ShopEntity;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Component
public class ShopMapper {

    private static final Logger LOGGER = Logger.getLogger(ShopMapper.class.getName());
    private final ModelMapper modelMapper;

    public ShopMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public ShopEntity from(Shop shop) {
        LOGGER.info("from(" + shop + ")");
        ShopEntity shopEntity = modelMapper.map(shop, ShopEntity.class);
        LOGGER.info("from(...) = " + shopEntity);
        return shopEntity;
    }

    public Shop from(ShopEntity shopEntity) {
        LOGGER.info("from(" + shopEntity + ")");
        Shop shop = modelMapper.map(shopEntity, Shop.class);
        LOGGER.info("from(...) = " + shop);
        return shop;
    }

    public List<Shop> fromEntities(List<ShopEntity> shopEntities) {
        LOGGER.info("fromEntities(size=" + (shopEntities == null ? 0 : shopEntities.size()) + ")");
        if (shopEntities == null) {
            return Collections.emptyList();
        }
        List<Shop> shops = shopEntities.stream()
                .map(this::from)
                .collect(Collectors.toList());
        LOGGER.info("fromEntities(...) returns " + shops.size() + " shops");
        return shops;
    }
}