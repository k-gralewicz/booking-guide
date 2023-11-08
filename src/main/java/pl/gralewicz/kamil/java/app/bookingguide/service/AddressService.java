package pl.gralewicz.kamil.java.app.bookingguide.service;

import pl.gralewicz.kamil.java.app.bookingguide.controller.model.Address;
import pl.gralewicz.kamil.java.app.bookingguide.dao.entity.AddressEntity;
import pl.gralewicz.kamil.java.app.bookingguide.dao.repository.AddressRepository;
import pl.gralewicz.kamil.java.app.bookingguide.service.mapper.AddressMapper;

import java.util.logging.Logger;

public class AddressService {
    private static final Logger LOGGER = Logger.getLogger(AddressService.class.getName());

//    private String name;
    private AddressRepository addressRepository; // zależność/dependency

    public AddressService(AddressRepository addressRepository) { //wstrzykiwanie zależności / dependency injection
        this.addressRepository = addressRepository;
    }

    public Address create(Address address) {
        LOGGER.info("create()");
        // TODO: 18.10.2023 zastąpić DAO respository - zamiast DAO użyjemy Respository - zmodyfikować DID.
//        używając repository trzeba będzie skorzystać z własnych mapperów.
//        wyżej opisany to do dzieje się w service.
        AddressMapper addressMapper = new AddressMapper();
        AddressEntity addressEntity = addressMapper.from(address);
        AddressEntity createdAddress =
                addressRepository.create(addressEntity); // delegacja / delegation
        LOGGER.info("create(...)=" + createdAddress);
        return null;
    }

}
// TODO: 02.08.2023 zrobić wstrzykiwanie zależności dla klasy ClientService,
//dodać test jednostkowy dla klasy ClientService.