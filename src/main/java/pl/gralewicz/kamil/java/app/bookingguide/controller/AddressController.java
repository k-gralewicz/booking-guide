package pl.gralewicz.kamil.java.app.bookingguide.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.gralewicz.kamil.java.app.bookingguide.controller.model.Address;
import pl.gralewicz.kamil.java.app.bookingguide.service.AddressService;

import java.util.List;
import java.util.logging.Logger;

@Controller
@RequestMapping(value = "/addresses")
public class AddressController {
    private static final Logger LOGGER = Logger.getLogger(AddressController.class.getName());

    private AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @GetMapping
    public String list(ModelMap modelMap) {
        LOGGER.info("list()");
        List<Address> addresses = addressService.list();
        modelMap.addAttribute("addresses", addresses);
        LOGGER.info("list(...)" + addresses);
        return "addresses";
    }

    @GetMapping(value = "/create")
    public String createView(ModelMap modelMap) {
        LOGGER.info("createView()");
        modelMap.addAttribute("createMassage", "Fill out the form fields");
        LOGGER.info("createView(...)= ");
        return "address-create";
    }

    @PostMapping
    public String create(Address address) {
        LOGGER.info("create(" + address + ")");
        Address createdAddress = addressService.create(address);
        LOGGER.info("create(...)= " + createdAddress);
        return "redirect:/addresses";
    }

    @GetMapping(value = "/id")
    public String read(@PathVariable Long id, String street, String flatNumber, String postCode, String city, String country, ModelMap modelMap) {
        LOGGER.info("read(" + id + ")");
        LOGGER.info("read(" + street + ")");
        LOGGER.info("read(" + flatNumber + ")");
        LOGGER.info("read(" + postCode + ")");
        LOGGER.info("read(" + city + ")");
        LOGGER.info("read(" + country + ")");
        Address readAddress = addressService.read(id);
        modelMap.addAttribute("createMessage", "This is address: " + readAddress);
        LOGGER.info("read(...)= ");
        return "address-read.html";
    }

    @GetMapping(value = "/update/{id}")
    public String updateView(@PathVariable Long id, ModelMap modelMap) {
        LOGGER.info("updateView()");
        Address readAddress = addressService.read(id);
        LOGGER.info("updateView(...)= " + readAddress);
        return "address-create";
    }

    @GetMapping(value = "/delete/id")
    public String delete(@PathVariable Long id, String street, String flatNumber, String postCode, String city, String country, ModelMap modelMap) {
        LOGGER.info("read(" + id + ")");
        LOGGER.info("read(" + street + ")");
        LOGGER.info("read(" + flatNumber + ")");
        LOGGER.info("read(" + postCode + ")");
        LOGGER.info("read(" + city + ")");
        LOGGER.info("read(" + country + ")");
        addressService.delete(id);
        return "redirect:/addresses";
    }

//    public Address create(Address address) throws Exception {
//        LOGGER.info("create()");
//        if (address != null) {
//            //walidacja danych przyjmowanych od użytkownika
//            if (address.getStreet() == null) {
//                LOGGER.warning("Please provide street for the address");
//                // zgłaszanie wyjątku - własnego wyjątku
//                throw new Exception("Please provide street for the address");
//            }
//        }
//
//        Address createdAddress = addressService.create(address);
//        LOGGER.info("create(...)=" + createdAddress);
//        return createdAddress;
//    }


//    komunikacja frontend - backend
//    FORM -> Submit -> GET /visits/create
//
//    komunikacja backend - frontend
//    modelMap -> addAttribute() -> html -> EL (Expression Language)
}
