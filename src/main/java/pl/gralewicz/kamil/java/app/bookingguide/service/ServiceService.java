package pl.gralewicz.kamil.java.app.bookingguide.service;

import pl.gralewicz.kamil.java.app.bookingguide.controller.model.Service;
import pl.gralewicz.kamil.java.app.bookingguide.dao.entity.ServiceEntity;
import pl.gralewicz.kamil.java.app.bookingguide.dao.repository.ServiceRepository;
import pl.gralewicz.kamil.java.app.bookingguide.service.mapper.ServiceMapper;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@org.springframework.stereotype.Service
public class ServiceService {
    private static final Logger LOGGER = Logger.getLogger(ServiceService.class.getName());

    private ServiceRepository serviceRepository;
    private ServiceMapper serviceMapper;

    public ServiceService(ServiceRepository serviceRepository, ServiceMapper serviceMapper) {
        this.serviceRepository = serviceRepository;
        this.serviceMapper = serviceMapper;
    }

    public List<Service> list() {
        LOGGER.info("list()");
        List<ServiceEntity> serviceEntities = serviceRepository.findAll();
        List<Service> services = serviceMapper.fromEntities(serviceEntities);
        LOGGER.info("list(...)= " + services);
        return services;
    }

    public Service create(Service service) {
        LOGGER.info("create()");
        ServiceEntity serviceEntity = serviceMapper.from(service);
        ServiceEntity createdServiceEntity = serviceRepository.save(serviceEntity);
        Service mappedService = serviceMapper.from(createdServiceEntity);
        LOGGER.info("create(...) =" + mappedService);
        return mappedService;
    }

    public Service read(Long id) {
        LOGGER.info("read(" + id + ")");
        Optional<ServiceEntity> optionalServiceEntity = serviceRepository.findById(id);
        ServiceEntity serviceEntity = optionalServiceEntity.orElseThrow();
        LOGGER.info("read(...)= ");
        return null;
    }

    public void delete(Long id) {
        LOGGER.info("delete(" + id + ")");
        serviceRepository.deleteById(id);
        LOGGER.info("delete(...)= ");
    }
}
