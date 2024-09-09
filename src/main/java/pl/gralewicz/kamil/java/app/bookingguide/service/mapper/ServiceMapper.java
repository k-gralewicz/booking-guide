package pl.gralewicz.kamil.java.app.bookingguide.service.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import pl.gralewicz.kamil.java.app.bookingguide.controller.model.Service;
import pl.gralewicz.kamil.java.app.bookingguide.dao.entity.ServiceEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Component
public class ServiceMapper {

    private static final Logger LOGGER = Logger.getLogger(ServiceMapper.class.getName());

    public List<Service> fromEntities(List<ServiceEntity> serviceEntities) {
        LOGGER.info("fromEntities()");

        List<Service> services = new ArrayList<>();

        for (ServiceEntity serviceEntity : serviceEntities) {
            Service service = from(serviceEntity);
            services.add(service);
        }

        LOGGER.info("fromEntities(...)= " + services);
        return services;
    }

    public ServiceEntity from(Service service) {
        LOGGER.info("from(" + service + ")");
        ModelMapper modelMapper = new ModelMapper();
        ServiceEntity serviceEntity = modelMapper.map(service, ServiceEntity.class);
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

