package pl.gralewicz.kamil.java.app.bookingguide.service;

import org.springframework.transaction.annotation.Transactional;
import pl.gralewicz.kamil.java.app.bookingguide.controller.model.Service;
import pl.gralewicz.kamil.java.app.bookingguide.dao.entity.ServiceEntity;
import pl.gralewicz.kamil.java.app.bookingguide.dao.entity.ShopEntity;
import pl.gralewicz.kamil.java.app.bookingguide.dao.repository.ServiceRepository;
import pl.gralewicz.kamil.java.app.bookingguide.dao.repository.ShopRepository;
import pl.gralewicz.kamil.java.app.bookingguide.service.mapper.ServiceMapper;
import pl.gralewicz.kamil.java.app.bookingguide.service.mapper.ShopMapper;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.logging.Logger;

@org.springframework.stereotype.Service
public class ServiceService {
    private static final Logger LOGGER = Logger.getLogger(ServiceService.class.getName());

    private final ServiceRepository serviceRepository;
    private final ServiceMapper serviceMapper;
    private final ShopRepository shopRepository;
    private final ShopMapper shopMapper;

    public ServiceService(ServiceRepository serviceRepository, ServiceMapper serviceMapper, ShopRepository shopRepository, ShopMapper shopMapper) {
        this.serviceRepository = serviceRepository;
        this.serviceMapper = serviceMapper;
        this.shopRepository = shopRepository;
        this.shopMapper = shopMapper;
    }

    public List<Service> list() {
        LOGGER.info("list()");
        List<ServiceEntity> serviceEntities = serviceRepository.findAll();
        List<Service> services = serviceMapper.fromEntities(serviceEntities);
        LOGGER.info("list(...)= " + services);
        return services;
    }

    public Service findById(Long id) {
        LOGGER.info("findById()");
        Optional<ServiceEntity> optionalServiceEntity = serviceRepository.findById(id);
        ServiceEntity serviceEntity = optionalServiceEntity.orElseThrow();
        Service service = serviceMapper.from(serviceEntity);
        LOGGER.info("findById(...)=" + service);
        return service;
    }

    public Service create(Service service) {
        LOGGER.info("create()");
        ServiceEntity serviceEntity = serviceMapper.from(service);
        ServiceEntity createdServiceEntity = serviceRepository.save(serviceEntity);
        Service mappedService = serviceMapper.from(createdServiceEntity);
        LOGGER.info("create(...) =" + mappedService);
        return mappedService;
    }

    @Transactional
    public Service createWithShop(Service service, Long shopId) {
        LOGGER.info("createWithShop(" + service + ", " + shopId + ")");
        Optional<ShopEntity> optionalShopEntity = shopRepository.findById(shopId);
        ShopEntity shopEntity = optionalShopEntity.orElseThrow();
        ServiceEntity serviceEntity = serviceMapper.from(service);
        serviceEntity.addShop(shopEntity);
        ServiceEntity createdServiceEntity = serviceRepository.save(serviceEntity);
        Service createdService = serviceMapper.from(createdServiceEntity);
        LOGGER.info("createWithShop(...) =" + createdService);
        return createdService;
    }

    public Service read(Long id) {
        LOGGER.info("read(" + id + ")");
        Optional<ServiceEntity> optionalServiceEntity = serviceRepository.findById(id);
        ServiceEntity serviceEntity = optionalServiceEntity
                .orElseThrow(() -> new NoSuchElementException("Nie znaleziono service o ID " + id));
        Service mappedService = serviceMapper.from(serviceEntity);
        LOGGER.info("read(...)= " + mappedService);
        return mappedService;
    }

    public void delete(Long id) {
        LOGGER.info("delete(" + id + ")");
        serviceRepository.deleteById(id);
        LOGGER.info("delete(...)= ");
    }
}
