package pl.gralewicz.kamil.java.app.bookingguide.service.mapper;

import pl.gralewicz.kamil.java.app.bookingguide.controller.model.Visit;
import pl.gralewicz.kamil.java.app.bookingguide.dao.entity.ClientEntity;
import pl.gralewicz.kamil.java.app.bookingguide.dao.entity.ShopEntity;
import pl.gralewicz.kamil.java.app.bookingguide.dao.entity.VisitEntity;

import java.util.logging.Logger;

public class VisitMapper {

    private static final Logger LOGGER = Logger.getLogger(VisitMapper.class.getName());

    public VisitEntity from(Visit visit) {
        LOGGER.info("from(" + visit + ")");
        VisitEntity visitEntity = new VisitEntity();
        visitEntity.setId(visit.getId());
        visitEntity.setDueDate(visit.getDueDate());

        ClientMapper clientMapper = new ClientMapper();
        ClientEntity clientEntity = clientMapper.from(visit.getClient());

        ShopMapper shopMapper = new ShopMapper();
        ShopEntity shopEntity = shopMapper.from(visit.getShop());

        visitEntity.setClient(clientEntity);
        visitEntity.setShop(shopEntity);
        LOGGER.info("from(...) = " + visitEntity);
        return visitEntity;
    }
}
