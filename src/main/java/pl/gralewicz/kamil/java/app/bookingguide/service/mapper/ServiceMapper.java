package pl.gralewicz.kamil.java.app.bookingguide.service.mapper;

import org.modelmapper.ModelMapper;
import pl.gralewicz.kamil.java.app.bookingguide.controller.model.Service;
import pl.gralewicz.kamil.java.app.bookingguide.dao.entity.ServiceEntity;

import java.util.logging.Logger;

public class ServiceMapper {

    private static final Logger LOGGER = Logger.getLogger(ServiceMapper.class.getName());

    public ServiceEntity from(Service service) {
        LOGGER.info("from(" + service + ")");
        ServiceEntity serviceEntity = new ServiceEntity();
        serviceEntity.setId(service.getId());
        serviceEntity.setName(service.getName());
        LOGGER.info("from(...) = " + serviceEntity);
        return serviceEntity;
    }

    public Service from(ServiceEntity serviceEntity) {
        LOGGER.info("from(" + serviceEntity + ")");
        ModelMapper modelMapper = new ModelMapper();
        Service service = modelMapper.map(serviceEntity, Service.class);
        LOGGER.info("from(...) = " + serviceEntity);
        return service;
    }
}

