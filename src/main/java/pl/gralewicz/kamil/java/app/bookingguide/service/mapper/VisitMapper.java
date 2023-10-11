package pl.gralewicz.kamil.java.app.bookingguide.service.mapper;

import org.modelmapper.ModelMapper;
import pl.gralewicz.kamil.java.app.bookingguide.controller.model.Visit;
import pl.gralewicz.kamil.java.app.bookingguide.dao.entity.VisitEntity;

import java.util.logging.Logger;

public class VisitMapper {

    private static final Logger LOGGER = Logger.getLogger(VisitMapper.class.getName());

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
}
// TODO: 11.10.2023 przerobić wszystkie własne mappery na użycie klasy ModelMapper.