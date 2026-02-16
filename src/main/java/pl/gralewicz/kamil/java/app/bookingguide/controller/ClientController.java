package pl.gralewicz.kamil.java.app.bookingguide.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import pl.gralewicz.kamil.java.app.bookingguide.controller.model.Client;
import pl.gralewicz.kamil.java.app.bookingguide.controller.model.Service;
import pl.gralewicz.kamil.java.app.bookingguide.controller.model.Shop;
import pl.gralewicz.kamil.java.app.bookingguide.controller.model.Visit;
import pl.gralewicz.kamil.java.app.bookingguide.service.ClientService;
import pl.gralewicz.kamil.java.app.bookingguide.service.ServiceService;
import pl.gralewicz.kamil.java.app.bookingguide.service.ShopService;
import pl.gralewicz.kamil.java.app.bookingguide.service.VisitService;

import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import static pl.gralewicz.kamil.java.app.bookingguide.api.BookingGuideConstants.SERVICE_SESSION;
import static pl.gralewicz.kamil.java.app.bookingguide.api.BookingGuideConstants.SHOP_SESSION;

@Controller
@RequestMapping(value = "/clients")
@SessionAttributes({SHOP_SESSION, SERVICE_SESSION})
public class ClientController {
    private static final Logger LOGGER = Logger.getLogger(ClientController.class.getName());

    private ClientService clientService;
    private VisitService visitService;
    private ShopService shopService;
    private ServiceService serviceService;

    public ClientController(ClientService clientService, VisitService visitService, ShopService shopService, ServiceService serviceService) {
        this.clientService = clientService;
        this.visitService = visitService;
        this.shopService = shopService;
        this.serviceService = serviceService;
    }

    @GetMapping(value = "/dashboard")
    public String dashboard(@RequestParam(required = false) Long selectedShopId,
                            @RequestParam(required = false) Long selectedServiceId,
                            ModelMap modelMap, HttpSession session) {
        LOGGER.info("dashboard()");
        LOGGER.info("dashboard(" + selectedShopId + ")");
        LOGGER.info("dashboard(" + selectedServiceId + ")");

        Set<Shop> shops = shopService.list();
        modelMap.addAttribute("shops", shops);
        modelMap.addAttribute("selectedShopId", selectedShopId);
        modelMap.addAttribute("selectedServiceId", selectedServiceId);

        if (selectedShopId != null) {
            Shop selectedShop = shopService.read(selectedShopId);
            modelMap.addAttribute(SHOP_SESSION, selectedShop);

            Set<Service> selectedShopServices = selectedShop.getServices();
            modelMap.addAttribute("services", selectedShopServices);
            LOGGER.info("selectedShopServices: (" + selectedShopServices + ")");
        }
        if (selectedServiceId != null) {
            Service selectedService = serviceService.read(selectedServiceId);
            modelMap.addAttribute(SERVICE_SESSION, selectedService);
        }

        List<Visit> visits;
        if (selectedShopId == null) {
            visits = visitService.list();
            LOGGER.info("Displaying all visits (no shopId filter)");
        } else {
            visits = visitService.list(selectedShopId);
            LOGGER.info("Displaying visits filtered for shopId: " + selectedShopId);
        }

        modelMap.addAttribute("visits", visits);
        LOGGER.info("dashboard(...)= " + visits);

        return "client-dashboard";
    }

    @GetMapping
    public String list(ModelMap modelMap) {
        LOGGER.info("list()");
        List<Client> clients = clientService.list();
        modelMap.addAttribute("clients", clients);
        LOGGER.info("list(...)= " + clients);
        return "clients";
    }

    @GetMapping(value = "/create")
    public String createView(ModelMap modelMap) {
        LOGGER.info("createView()");
        modelMap.addAttribute("createMassage", "Fill out the form fields");
        modelMap.addAttribute("client", new Client());
        LOGGER.info("createView(...)= ");
        return "client-create";
    }

    @PostMapping
    public String create(@ModelAttribute Client client) {
        LOGGER.info("create(" + client + ")");
        Client createdClient = clientService.create(client);
        LOGGER.info("create(...)= " + createdClient);
        return "redirect:/clients";
    }

    @GetMapping(value = "/{id}")
    public String read(@PathVariable Long id, ModelMap modelMap) {
        LOGGER.info("read(" + id + ")");
//        LOGGER.info("read(" + firstName + ")");
//        LOGGER.info("read(" + lastName + ")");
//        LOGGER.info("read(" + email + ")");
//        LOGGER.info("read(" + phoneNumber + ")");
//        LOGGER.info("read(" + address + ")");
        Client readClient = clientService.read(id);
        modelMap.addAttribute("client", readClient);
        modelMap.addAttribute("createMessage", "This is client: " + readClient);
        boolean isEdit = true;
        modelMap.addAttribute("isEdit", isEdit);
        LOGGER.info("read(...)= ");
        return "client-read.html";
    }

    @GetMapping(value = "/update/{id}")
    public String updateView(@PathVariable Long id, ModelMap modelMap) {
        LOGGER.info("updateView()");
        Client readClient = clientService.read(id);
        modelMap.addAttribute("client", readClient);
        modelMap.addAttribute("isEdit", true);
        LOGGER.info("updateView(...)= " + readClient);
        return "client-create";
    }

    @PostMapping(value = "/update/{id}")
    public String update(@PathVariable Long id, @ModelAttribute Client client) {
        LOGGER.info("update(" + id + "," + client + ")");
        Client updatedClient = clientService.update(client);
        LOGGER.info("update(...)= " + updatedClient);
        return "redirect:/clients";
    }

    @GetMapping(value = "/delete/{id}")
    public String delete(@PathVariable Long id, String firstName, String lastName, String email, String phoneNumber, String address, ModelMap modelMap) {
        LOGGER.info("delete(" + id + ")");
        LOGGER.info("delete(" + firstName + ")");
        LOGGER.info("delete(" + lastName + ")");
        LOGGER.info("delete(" + email + ")");
        LOGGER.info("delete(" + phoneNumber + ")");
        LOGGER.info("delete(" + address + ")");
        clientService.delete(id);
        return "redirect:/clients";
    }

    @GetMapping(value = "/visit/create")
    public String visitCreate(@RequestParam String username, Long selectedShopId, Long selectedServiceId, ModelMap modelMap) {
        LOGGER.info("visitCreate(" + username + ")");
        LOGGER.info("visitCreate(" + selectedShopId + ")");
        LOGGER.info("visitCreate(" + selectedServiceId + ")");

        // na podstawie selected IDs pobrać shop i service i wstawić do sesji.

        Shop selectedShop = shopService.read(selectedShopId);
        modelMap.addAttribute(SHOP_SESSION, selectedShop);

        Service selectedService = serviceService.read(selectedServiceId);
        modelMap.addAttribute(SERVICE_SESSION, selectedService);

        return "redirect:/visits/create";
    }

}
// TODO: 21.08.2024 poprawić controller

// TODO: 10.07.2025 :
// wybrany shopId przesłać do backendu
// w backend na podstawie shopId wybrać odpowiednie serwisy
// na frontend zwrócić shopID oraz serviceID i zaznaczyć odpowiednie opcje w dropdown.