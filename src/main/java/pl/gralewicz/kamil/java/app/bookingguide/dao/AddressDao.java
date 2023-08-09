package pl.gralewicz.kamil.java.app.bookingguide.dao;

import pl.gralewicz.kamil.java.app.bookingguide.controller.model.Address;
import pl.gralewicz.kamil.java.app.bookingguide.controller.model.Shop;

import java.util.List;
import java.util.logging.Logger;


public class AddressDao {
    private static final Logger LOGGER = Logger.getLogger(AddressDao.class.getName());

    public Address create(Address address) {
        LOGGER.info("create()");
        // TODO: 09.08.2023 dodać test jednostkowy dla metody create w AddressDao
        // stworzyć tabelę ADDRESS, która ma kolumny takie jak pola w klasie Address.
        // zaimplementować metodę create analogicznie do CarJdbcDao z pojektu java-learn.
        LOGGER.info("create(...)=");
        return null;
    }

    public Address read(Integer id) {
        LOGGER.info("read()");
        LOGGER.info("read(...)=");
        return null;
    }

    public Address update(Integer id, Address address) {
        LOGGER.info("update()");
        LOGGER.info("update()=");
        return null;
    }

    public void delete(Integer id) {
        LOGGER.info("delete()");
        LOGGER.info("delete()=");
    }

    public List<Address> list() {
        return null;
    }
}

// TODO: 19.07.2023 dokończyć szablon metod CRUD w AddressDao,
// zrobić warstwy dla każdego modelu poza Application i DurationType.