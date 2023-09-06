package pl.gralewicz.kamil.java.app.bookingguide.dao.repository;

import pl.gralewicz.kamil.java.app.bookingguide.dao.entity.AddressEntity;

import java.util.logging.Logger;

public class AddressRepository {

    private static final Logger LOGGER = Logger.getLogger(AddressRepository.class.getName());

    public AddressEntity create(AddressEntity address) {
        LOGGER.info("create()");
        return null;
    }
}
