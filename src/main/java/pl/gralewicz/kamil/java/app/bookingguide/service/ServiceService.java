package pl.gralewicz.kamil.java.app.bookingguide.service;

import pl.gralewicz.kamil.java.app.bookingguide.controller.model.Service;
import pl.gralewicz.kamil.java.app.bookingguide.dao.entity.ServiceEntity;
import pl.gralewicz.kamil.java.app.bookingguide.dao.repository.ServiceRepository;
import pl.gralewicz.kamil.java.app.bookingguide.service.mapper.ServiceMapper;

import java.util.logging.Logger;

public class ServiceService {
    private static final Logger LOGGER = Logger.getLogger(ServiceService.class.getName());

    private ServiceRepository serviceRepository;
    private ServiceMapper serviceMapper;

    public ServiceService(ServiceRepository serviceRepository, ServiceMapper serviceMapper) {
        this.serviceRepository = serviceRepository;
        this.serviceMapper = serviceMapper;
    }

    public Service create(Service service) {
        LOGGER.info("create()");
        ServiceEntity serviceEntity = serviceMapper.from(service);
        ServiceEntity createdServiceEntity = serviceRepository.create(serviceEntity);
        Service mappedService = serviceMapper.from(createdServiceEntity);
        LOGGER.info("create(...) =" + mappedService);
        return mappedService;
    }
}
