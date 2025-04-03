package pl.gralewicz.kamil.java.app.bookingguide.service;

import org.springframework.stereotype.Service;
import pl.gralewicz.kamil.java.app.bookingguide.controller.model.Address;
import pl.gralewicz.kamil.java.app.bookingguide.dao.entity.AddressEntity;
import pl.gralewicz.kamil.java.app.bookingguide.dao.repository.AddressRepository;
import pl.gralewicz.kamil.java.app.bookingguide.service.mapper.AddressMapper;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class AddressService {
    private static final Logger LOGGER = Logger.getLogger(AddressService.class.getName());

    private AddressRepository addressRepository; // zależność/dependency
    private AddressMapper addressMapper;

    public AddressService(AddressRepository addressRepository, AddressMapper addressMapper) { //wstrzykiwanie zależności / dependency injection
        this.addressRepository = addressRepository;
        this.addressMapper = addressMapper;
    }

    public List<Address> list() {
        LOGGER.info("list()");
        List<AddressEntity> addressEntities = addressRepository.findAll();
        List<Address> addresses = addressMapper.fromEntities(addressEntities);
        LOGGER.info("list(...)= " + addresses);
        return addresses;
    }

    public Address create(Address address) {
        LOGGER.info("create()");
        AddressEntity addressEntity = addressMapper.from(address);
        AddressEntity createdAddressEntity =
                addressRepository.save(addressEntity); // delegacja / delegation
        Address mappedAddress = addressMapper.from(createdAddressEntity);
        LOGGER.info("create(...)=" + mappedAddress);
        return mappedAddress;
    }

    public Address read(Long id) {
        LOGGER.info("read(" + id + ")");
        Optional<AddressEntity> optionalAddressEntity = addressRepository.findById(id);
        AddressEntity addressEntity = optionalAddressEntity.orElseThrow();
        Address mappedAddress = addressMapper.from(addressEntity);
        LOGGER.info("read(...) = " + mappedAddress);
        return mappedAddress;
    }

    public void delete(Long id) {
        LOGGER.info("delete(" + id + ")");
        addressRepository.deleteById(id);
        LOGGER.info("delete(...)= ");
    }
}
