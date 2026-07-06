package pl.gralewicz.kamil.java.app.bookingguide.service.mapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
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
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        VisitEntity visitEntity = modelMapper.map(visit, VisitEntity.class);

        LOGGER.info("from(...) = " + visitEntity);
        return visitEntity;
    }

    public Visit from(VisitEntity visitEntity) {
        LOGGER.info("from(" + visitEntity + ")");
        ModelMapper modelMapper = new ModelMapper();
        Visit visit = modelMapper.map(visitEntity, Visit.class);
        // POPRAWKA: Logujemy zmapowany obiekt 'visit', a nie ponownie 'visitEntity'
        LOGGER.info("from(...) = " + visit);
        return visit;
    }
}