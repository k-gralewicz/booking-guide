package pl.gralewicz.kamil.java.app.bookingguide.service;

import pl.gralewicz.kamil.java.app.bookingguide.controller.model.Address;
import pl.gralewicz.kamil.java.app.bookingguide.dao.entity.AddressEntity;
import pl.gralewicz.kamil.java.app.bookingguide.dao.repository.AddressRepository;
import pl.gralewicz.kamil.java.app.bookingguide.service.mapper.AddressMapper;

import java.util.logging.Logger;

public class AddressService {
    private static final Logger LOGGER = Logger.getLogger(AddressService.class.getName());

    private AddressRepository addressRepository; // zależność/dependency
    private AddressMapper addressMapper;

    public AddressService(AddressRepository addressRepository, AddressMapper addressMapper) { //wstrzykiwanie zależności / dependency injection
        this.addressRepository = addressRepository;
        this.addressMapper = addressMapper;
    }

    public Address create(Address address) {
        LOGGER.info("create()");
        AddressEntity addressEntity = addressMapper.from(address);
        AddressEntity createdAddressEntity =
                addressRepository.create(addressEntity); // delegacja / delegation
        Address mappedAddress = addressMapper.from(createdAddressEntity);
        LOGGER.info("create(...)=" + mappedAddress);
        return mappedAddress;
    }

}
