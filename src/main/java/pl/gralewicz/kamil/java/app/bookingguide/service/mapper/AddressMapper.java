package pl.gralewicz.kamil.java.app.bookingguide.service.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import pl.gralewicz.kamil.java.app.bookingguide.controller.model.Address;
import pl.gralewicz.kamil.java.app.bookingguide.dao.entity.AddressEntity;

import java.util.logging.Logger;

@Component
public class AddressMapper {

    private static final Logger LOGGER = Logger.getLogger(AddressMapper.class.getName());

    public AddressEntity from(Address address) {
        LOGGER.info("from(" + address + ")");
        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setId(address.getId());
        addressEntity.setCity(address.getCity());
        LOGGER.info("from(...) = " + addressEntity);
        return addressEntity;
    }

    public Address from(AddressEntity addressEntity) {
        LOGGER.info("from(" + addressEntity + ")");
        ModelMapper modelMapper = new ModelMapper();
        Address address = modelMapper.map(addressEntity, Address.class);
        LOGGER.info("from(...) = " + address);
        return address;
    }
}

// TODO: 04.10.2023 stworzyć mappery dla wszystkich modeli i encji (stworzyć nowe klasy mapperów)
// dla każdego mappera stworzyć test jednostkowy.
// opcjonalnie: zamiast get/set użyć biblioteki ModelMapper.