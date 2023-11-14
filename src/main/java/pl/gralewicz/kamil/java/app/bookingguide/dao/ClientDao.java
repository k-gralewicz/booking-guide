package pl.gralewicz.kamil.java.app.bookingguide.dao;

import pl.gralewicz.kamil.java.app.bookingguide.controller.model.Client;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

public class ClientDao {
    private static final Logger LOGGER = Logger.getLogger(ClientDao.class.getName());

    public Client create(Client client) {
        LOGGER.info("create()");
        // TODO: 09.08.2023 dodać test jednostkowy dla metody create w ClientDao
        // stworzyć tabelę CLIENTS, która ma kolumny takie jak pola w klasie Client.
        // zaimplementować metodę create analogicznie do CarJdbcDao z pojektu java-learn.

        try {
            Connection connection = DriverManager.getConnection("jdbc:h2:~/test", "sa", "");
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO CLIENT( ID , FIRST_NAME , LAST_NAME , EMAIL , PHONE_NUMBER , ADDRESS ) VALUES(?, ?, ?, ?, ?, ?);");
            preparedStatement.setInt(1, RandomIdGenerator.generate());
            preparedStatement.setString(2, client.getFirstName());
            preparedStatement.setString(3, client.getLastName());
            preparedStatement.setString(4, client.getEmail());
            preparedStatement.setString(5, client.getPhoneNumber());
            preparedStatement.setString(6, null);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        LOGGER.info("create(...)=");
        return null;
    }

    public Client read(Integer id) {
        LOGGER.info("read()");
        LOGGER.info("read(...)=");
        return null;
    }

    public Client update(Integer id, Client client) {
        LOGGER.info("update()");
        LOGGER.info("update(...)=");
        return null;
    }

    public Client delete(Integer id) {
        LOGGER.info("delete()");
        LOGGER.info("delete(...)=");
        return null;
    }

    public List<Client> clientList() {
        return null;
    }
}
