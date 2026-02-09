package pl.gralewicz.kamil.java.app.bookingguide.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import pl.gralewicz.kamil.java.app.bookingguide.controller.model.*;
import pl.gralewicz.kamil.java.app.bookingguide.service.*;

import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import static pl.gralewicz.kamil.java.app.bookingguide.api.BookingGuideConstants.SERVICE_SESSION;
import static pl.gralewicz.kamil.java.app.bookingguide.api.BookingGuideConstants.SHOP_SESSION;

@Controller // przyjmuje dane od użytkownika i zwraca dane do użytkownika za pomocą protokołu http
@RequestMapping(value = "/visits")
@SessionAttributes({SHOP_SESSION, SERVICE_SESSION})
public class VisitController {
    private static final Logger LOGGER = Logger.getLogger(VisitController.class.getName());
    private final ClientService clientService;

    private VisitService visitService;
    private ServiceService serviceService;
    private ShopService shopService;
    private UserService userService;

    public VisitController(VisitService visitService, ServiceService serviceService, ShopService shopService, UserService userService, ClientService clientService) {
        this.visitService = visitService;
        this.serviceService = serviceService;
        this.shopService = shopService;
        this.userService = userService;
        this.clientService = clientService;
    }

    @GetMapping
    public String list(ModelMap modelMap) {
        LOGGER.info("list()");
        List<Visit> visits = visitService.list();
        modelMap.addAttribute("visits", visits);
        LOGGER.info("list(...)= " + visits);
        return "visits";
    }

    @GetMapping(value = "/create")
    public String createView(String username, Long serviceId, ModelMap modelMap) {
        LOGGER.info("createView()");
//        User userByUsername = userService.findByUsername(username);
//        if (userByUsername == null) {
//            modelMap.addAttribute("error", "User not found");
//            return "visit-create";
//        }
        List<Service> services = serviceService.list();
        Set<Shop> shops = shopService.list();
        modelMap.addAttribute("services", services);
        modelMap.addAttribute("shops", shops);
        modelMap.addAttribute("visit", new Visit());
        modelMap.addAttribute("isEdit", false);
        LOGGER.info("createView(...)= ");
        return "visit-create";
    }

    @PostMapping(value = "/create")
    public String createX(String username, Long shopId, Long serviceId, String dueDate, ModelMap modelMap) {
        LOGGER.info("createX(" + username + ")");
        LOGGER.info("createX(" + shopId + ")");
        LOGGER.info("createX(" + serviceId + ")");
        LOGGER.info("createX(" + dueDate + ")");
        // na podstawie username pobrać użytkownika
        User userByUsername = userService.findByUsername(username);
        if (userByUsername == null) {
            modelMap.addAttribute("error", "User not found");
            return "visit-create";
        }
        // na podstawie shopId pobrać shop,
//        shopId = 602L;
        Shop shop = shopService.findById(shopId);
        if (shop == null) {
            modelMap.addAttribute("error", "Shop not found");
            return "visit-create";
        }
        Service service = serviceService.findById(serviceId);
        if (service == null) {
            modelMap.addAttribute("error", "Service not found");
            return "visit-create";
        }
        // na podstawie serviceId pobrać service

        Visit newVisit = new Visit();
        newVisit.setShop(shop);
        newVisit.setService(service);
//        newVisit.setDueDate(LocalDate.from(LocalDate.parse(dueDate, DateTimeFormatter.ISO_DATE).atStartOfDay()));

        Visit createdVisit = visitService.create(newVisit);
        LOGGER.info("createX(...)= " + createdVisit);
        return "redirect:/visits";
    }

    @PostMapping(value = "/create/user")
    public String createWithUsername(@RequestParam String username, ModelMap modelMap) {
        LOGGER.info("createWithUsername(" + username + ")");
        User userByUsername = userService.findByUsername(username);
        if (userByUsername == null) {
            modelMap.addAttribute("error", "User not found");
            return "visit-create";
        }
        List<Service> services = serviceService.list();
        Set<Shop> shops = shopService.list();
        modelMap.addAttribute("services", services);
        modelMap.addAttribute("shops", shops);
        modelMap.addAttribute("visit", new Visit());
        modelMap.addAttribute("isEdit", false);
        LOGGER.info("createWithUsername(...)= ");
        return "visit-create";
    }

// TODO: 13.03.2025 poniżej: 
    // 1. Użytkownik wybiera salon(shop),
    // 2. Użytkownik wybiera usługę(service) dla danego salonu(shop),
    //      2a. Filtrowanie usług po salonie,
    // 3. Użytkownik wybiera datę,
    //      3a. Sprawdzanie dostępności,
    // 4. Użytkownik potwierdza rezerwację wizyty(visit),
    //      4a. Blokada godzinowa usługi w danym salonie.

    @GetMapping(value = "/create/{id}")
    public String createWithService(@PathVariable(name = "id") Long serviceId, Long shopId, Long clientId, ModelMap modelMap) {
        LOGGER.info("createWithService(" + serviceId + ")");
        LOGGER.info("createWithService(" + shopId + ")");
        LOGGER.info("createWithService(" + clientId + ")");
        Service service = serviceService.read(serviceId);
        modelMap.addAttribute(SERVICE_SESSION, service);
        List<Client> clients = clientService.list();
        Visit visit = new Visit();
        visit.setService(service);

        LOGGER.info("createWithService(" + visit + ")");

        modelMap.addAttribute("visit", visit);
        modelMap.addAttribute("isEdit", false);
        modelMap.addAttribute("clients", clients);
        LOGGER.info("createWithService(...)= ");
        return "visit-create.html";
    }

    @PostMapping
    public String create(@ModelAttribute Visit visit, Long shopId) {
        LOGGER.info("create(" + visit + ", " + shopId + ")");


        Visit createdVisit = visitService.create(visit);
        LOGGER.info("create(...)= " + createdVisit);
        return "redirect:/visits";
    }

    //metoda obsługująca żadanie GET protokołu HTTP,
    @GetMapping(value = "/{id}")
    public String read(@PathVariable Long id, ModelMap modelMap) { //id paramentr żądania prtokołu http w postaci zmiennej parametru URL.
        LOGGER.info("read(" + id + ")");
        Visit readVisit = visitService.read(id);
        modelMap.addAttribute("createMessage", "This is visit: " + readVisit);
        boolean isEdit = true;
        modelMap.addAttribute("isEdit", isEdit); // modelMap służy do komunikacji backend-frontend, ustawia zmienne(wartości) widoczne na frontendzie.
        LOGGER.info("read(...)= ");
        return "visit-read.html";
    }

    @GetMapping(value = "/update/{id}")
    public String updateView(@PathVariable Long id, ModelMap modelMap) {
        LOGGER.info("updateView(" + id + ")");
        Visit readVisit = visitService.read(id);
        modelMap.addAttribute("visit", readVisit);
        LOGGER.info("readVisit= " + readVisit);
        if (readVisit != null) {
            Service service = readVisit.getService();
            modelMap.addAttribute(SERVICE_SESSION, service);
        }

        modelMap.addAttribute("isEdit", true);
        LOGGER.info("updateView(...)= " + readVisit);
        return "visit-create.html";
    }

    @PostMapping(value = "/update/{id}")
    public String update(@PathVariable Long id, @ModelAttribute Visit visit) {
        LOGGER.info("update(" + id + "," + visit + ")");
        Visit updatedVisit = visitService.update(id, visit);
        LOGGER.info("update(...)= " + updatedVisit);
        return "redirect:/visits";
    }

    // parametry session.SERVICE_SESSION i session.SHOP_SESSION muszą być dostępne w metodzie update analogicznie jak w create.

    @GetMapping(value = "/delete/{id}")
    public String delete(@PathVariable Long id, ModelMap modelMap) {
        LOGGER.info("delete(" + id + ")");
        visitService.delete(id);
        return "redirect:/visits";
    }
}
