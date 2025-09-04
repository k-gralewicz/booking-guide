package pl.gralewicz.kamil.java.app.bookingguide.service.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import pl.gralewicz.kamil.java.app.bookingguide.controller.model.Shop;
import pl.gralewicz.kamil.java.app.bookingguide.dao.entity.ShopEntity;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Component
public class ShopMapper {

    private static final Logger LOGGER = Logger.getLogger(ShopMapper.class.getName());

    public ShopEntity from(Shop shop) {
        LOGGER.info("from(" + shop + ")");
        ModelMapper modelMapper = new ModelMapper();
        ShopEntity shopEntity = modelMapper.map(shop, ShopEntity.class);
        LOGGER.info("from(...) = " + shopEntity);
        return shopEntity;
    }

    public Shop from(ShopEntity shopEntity) {
        LOGGER.info("from(" + shopEntity + ")");
        ModelMapper modelMapper = new ModelMapper();
        Shop shop = modelMapper.map(shopEntity, Shop.class);
        LOGGER.info("from(...) = " + shop);
        return shop;
    }

    public Set<Shop> fromEntities(Collection<ShopEntity> shopEntities) {
        LOGGER.info("fromEntities(size=" + (shopEntities == null ? 0 : shopEntities.size()) + ")");
        if (shopEntities == null) {
            return Collections.emptySet();
        }
        Set<Shop> shops = shopEntities.stream()
                .map(this::from)
                .collect(Collectors.toSet());
        LOGGER.info("fromEntities(...) returns " + shops.size() + " shops");
        return shops;
    }
}