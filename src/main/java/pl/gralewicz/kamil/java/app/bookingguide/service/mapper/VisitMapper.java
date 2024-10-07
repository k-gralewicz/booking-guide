package pl.gralewicz.kamil.java.app.bookingguide.service.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import pl.gralewicz.kamil.java.app.bookingguide.controller.model.Visit;
import pl.gralewicz.kamil.java.app.bookingguide.dao.entity.VisitEntity;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Component
public class VisitMapper {

    private static final Logger LOGGER = Logger.getLogger(VisitMapper.class.getName());

    public List<Visit> fromEntities(List<VisitEntity> visitEntities) {
        LOGGER.info("fromEntities()");

        List<Visit> visits = visitEntities.stream()
                .map((visit) -> from(visit))
                .collect(Collectors.toList());

        LOGGER.info("fromEntities(...)= " + visits);
        return visits;
    }

    public VisitEntity from(Visit visit) {
        LOGGER.info("from(" + visit + ")");

        ModelMapper modelMapper = new ModelMapper();
        VisitEntity visitEntity = modelMapper.map(visit, VisitEntity.class);

//        VisitEntity visitEntity = new VisitEntity();
//        visitEntity.setId(visit.getId());
//        visitEntity.setDueDate(visit.getDueDate());
//
//        ClientMapper clientMapper = new ClientMapper();
//        ClientEntity clientEntity = clientMapper.from(visit.getClient());
//
//        ShopMapper shopMapper = new ShopMapper();
//        ShopEntity shopEntity = shopMapper.from(visit.getShop());
//
//        visitEntity.setClient(clientEntity);
//        visitEntity.setShop(shopEntity);
        LOGGER.info("from(...) = " + visitEntity);
        return visitEntity;
    }

    public Visit from(VisitEntity visitEntity) {
        LOGGER.info("from(" + visitEntity + ")");
        ModelMapper modelMapper = new ModelMapper();
        Visit visit = modelMapper.map(visitEntity, Visit.class);
        LOGGER.info("from(...) = " + visitEntity);
        return visit;
    }
}
// TODO: 11.10.2023 przerobić wszystkie własne mappery na użycie klasy ModelMapper.