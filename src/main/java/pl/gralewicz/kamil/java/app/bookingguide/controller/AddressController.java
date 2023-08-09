package pl.gralewicz.kamil.java.app.bookingguide.controller;

import pl.gralewicz.kamil.java.app.bookingguide.controller.model.Address;
import pl.gralewicz.kamil.java.app.bookingguide.dao.AddressDao;
import pl.gralewicz.kamil.java.app.bookingguide.service.AddressService;

import java.util.logging.Logger;

public class AddressController {
    private static final Logger LOGGER = Logger.getLogger(AddressController.class.getName());

    private AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    public Address create(Address address) {
        LOGGER.info("create()");
        Address createdAddress = addressService.create(address);
        LOGGER.info("create(...)=" + createdAddress);
        return createdAddress;
    }

}
