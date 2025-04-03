package pl.gralewicz.kamil.java.app.bookingguide.service;

import org.springframework.stereotype.Service;
import pl.gralewicz.kamil.java.app.bookingguide.controller.model.Shop;
import pl.gralewicz.kamil.java.app.bookingguide.dao.entity.ShopEntity;
import pl.gralewicz.kamil.java.app.bookingguide.dao.repository.ShopRepository;
import pl.gralewicz.kamil.java.app.bookingguide.service.mapper.ShopMapper;

import java.util.List; // Dodaj import dla List
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class ShopService {
    private static final Logger LOGGER = Logger.getLogger(ShopService.class.getName());

    private final ShopRepository shopRepository;
    private final ShopMapper shopMapper;

    public ShopService(ShopRepository shopRepository, ShopMapper shopMapper) {
        this.shopRepository = shopRepository;
        this.shopMapper = shopMapper;
    }

    public List<Shop> list() {
        LOGGER.info("list()");
        List<ShopEntity> shopEntities = shopRepository.findAll();
        List<Shop> shops = shopMapper.fromEntities(shopEntities);
        LOGGER.info("list(...) found " + shops.size() + " shops"); // Lepszy log
        return shops;
    }

    public Shop findById(Long id){
        LOGGER.info("findById(" + id +")");
        Optional<ShopEntity> optionalShopEntity = shopRepository.findById(id);
        ShopEntity shopEntity = optionalShopEntity
                .orElseThrow(() -> new NoSuchElementException("Nie znaleziono sklepu o ID: " + id));
        Shop shop = shopMapper.from(shopEntity);
        LOGGER.info("findById(...)=" + shop);
        return shop;
    }

    public Shop create(Shop shop) {
        LOGGER.info("create()");
        ShopEntity shopEntity = shopMapper.from(shop);
        ShopEntity createdShopEntity = shopRepository.save(shopEntity);
        Shop mappedShop = shopMapper.from(createdShopEntity);
        LOGGER.info("create(...)=" + mappedShop);
        return mappedShop;
    }

    public Shop read(Long id) {
        LOGGER.info("read(" + id + ")");
        Optional<ShopEntity> optionalShopEntity = shopRepository.findById(id);
        ShopEntity shopEntity = optionalShopEntity
                .orElseThrow(() -> new NoSuchElementException("Nie znaleziono sklepu o ID: " + id));
        Shop shop = shopMapper.from(shopEntity);
        LOGGER.info("read(...)= " + shop);
        return shop;
    }

    public void delete(Long id) {
        LOGGER.info("delete(" + id + ")");
        if (!shopRepository.existsById(id)) {
            LOGGER.warning("Próba usunięcia nieistniejącego sklepu o ID: " + id);
            return;
        }
        shopRepository.deleteById(id);
        LOGGER.info("delete(...) completed for ID: " + id);
    }
}
