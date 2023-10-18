package pl.gralewicz.kamil.java.app.bookingguide.service;

import pl.gralewicz.kamil.java.app.bookingguide.controller.model.Address;
import pl.gralewicz.kamil.java.app.bookingguide.dao.AddressDao;

import java.util.logging.Logger;

public class AddressService {
    private static final Logger LOGGER = Logger.getLogger(AddressService.class.getName());

//    private String name;
    private AddressDao addressDao; // zależność/dependency

    public AddressService(AddressDao addressDao) { //wstrzykiwanie zależności / dependency injection
        this.addressDao = addressDao;
    }

    public Address create(Address address) {
        LOGGER.info("create()");
        // TODO: 18.10.2023 zastąpić DAO respository - zamiast DAO użyjemy Respository - zmodyfikować DID.
//        używając repository trzeba będzie skorzystać z własnych mapperów.
//        wyżej opisany to do dzieje się w service.
        Address createdAddress = addressDao.create(address); // delegacja / delegation
        LOGGER.info("create(...)=" + createdAddress);
        return createdAddress;
    }

}
// TODO: 02.08.2023 zrobić wstrzykiwanie zależności dla klasy ClientService,
//dodać test jednostkowy dla klasy ClientService.