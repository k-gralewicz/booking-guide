package pl.gralewicz.kamil.java.app.bookingguide.dao;

import pl.gralewicz.kamil.java.app.bookingguide.controller.model.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

public class ServiceDao {
    private static final Logger LOGGER = Logger.getLogger(ServiceDao.class.getName());

    public Service create(Service service) {
        LOGGER.info("create()");

        try {
            Connection connection = DriverManager.getConnection("jdbc:h2:~/test", "sa", "sa");
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO SERVICE( ID , NAME , DESCRIPTION , PRICE , DURATION , DURATION_TYPE ) VALUES(?, ?, ?, ?, ?, ?);");
            preparedStatement.setInt(1, RandomIdGenerator.generate());
            preparedStatement.setString(2, service.getName());
            preparedStatement.setString(3, service.getDescription());
            preparedStatement.setBigDecimal(4, service.getPrice());
            preparedStatement.setInt(5, service.getDuration());
            // TODO: 11.09.2023 uniknąć null pointer exception dla poniższej linijki: 
//            preparedStatement.setString(6, service.getDurationType().getLabel());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        LOGGER.info("create(...)=");
        return null;
    }

    public Service read(Integer id) {
        LOGGER.info("read()");
        LOGGER.info("read(...)=");
        return null;
    }

    public Service update(Integer id, Service service) {
        LOGGER.info("update()");
        LOGGER.info("update()=");
        return null;
    }

    public void delete(Integer id) {
        LOGGER.info("delete()");
        LOGGER.info("delete()=");
    }

    public List<Service> list() {
        return null;
    }
}

