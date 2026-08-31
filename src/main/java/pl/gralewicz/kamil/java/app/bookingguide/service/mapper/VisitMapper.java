package pl.gralewicz.kamil.java.app.bookingguide.service.mapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;
import pl.gralewicz.kamil.java.app.bookingguide.controller.model.Visit;
import pl.gralewicz.kamil.java.app.bookingguide.dao.entity.ClientEntity;
import pl.gralewicz.kamil.java.app.bookingguide.dao.entity.ServiceEntity;
import pl.gralewicz.kamil.java.app.bookingguide.dao.entity.ShopEntity;
import pl.gralewicz.kamil.java.app.bookingguide.dao.entity.VisitEntity;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Component
public class VisitMapper {

    private static final Logger LOGGER = Logger.getLogger(VisitMapper.class.getName());
    private final ModelMapper modelMapper;

    public VisitMapper() {
        this.modelMapper = new ModelMapper();
        this.modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    public List<Visit> fromEntities(List<VisitEntity> visitEntities) {
        LOGGER.info("fromEntities()");

        if (visitEntities == null) {
            return List.of();
        }

        List<Visit> visits = visitEntities.stream()
                .map(this::from)
                .collect(Collectors.toList());

        LOGGER.info("fromEntities(...)= " + visits);
        return visits;
    }

    public VisitEntity from(Visit visit) {
        LOGGER.info("from(" + visit + ")");
        if (visit == null) {
            return null;
        }

        VisitEntity visitEntity = modelMapper.map(visit, VisitEntity.class);

        // Przypisujemy ID do obiektów encji relacyjnych w VisitEntity
        if (visit.getServiceId() != null) {
            ServiceEntity serviceEntity = new ServiceEntity();
            serviceEntity.setId(visit.getServiceId());
            visitEntity.setService(serviceEntity);
        }

        if (visit.getShopId() != null) {
            ShopEntity shopEntity = new ShopEntity();
            shopEntity.setId(visit.getShopId());
            visitEntity.setShop(shopEntity);
        }

        if (visit.getClientId() != null) {
            ClientEntity clientEntity = new ClientEntity();
            clientEntity.setId(visit.getClientId());
            visitEntity.setClient(clientEntity);
        }

        LOGGER.info("from(...) = " + visitEntity);
        return visitEntity;
    }

    public Visit from(VisitEntity visitEntity) {
        LOGGER.info("from(" + visitEntity + ")");
        if (visitEntity == null) {
            return null;
        }

        Visit visit = modelMapper.map(visitEntity, Visit.class);

        // Bezpieczne pobieranie ID z encji powiązanych
        if (visitEntity.getService() != null) {
            visit.setServiceId(visitEntity.getService().getId());
        }

        if (visitEntity.getShop() != null) {
            visit.setShopId(visitEntity.getShop().getId());
        }

        if (visitEntity.getClient() != null) {
            visit.setClientId(visitEntity.getClient().getId());
        }

        LOGGER.info("from(...) = " + visit);
        return visit;
    }
}