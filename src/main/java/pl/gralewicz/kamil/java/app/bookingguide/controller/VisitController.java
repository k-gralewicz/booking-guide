package pl.gralewicz.kamil.java.app.bookingguide.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.gralewicz.kamil.java.app.bookingguide.controller.model.Service;
import pl.gralewicz.kamil.java.app.bookingguide.controller.model.Shop;
import pl.gralewicz.kamil.java.app.bookingguide.controller.model.User;
import pl.gralewicz.kamil.java.app.bookingguide.controller.model.Visit;
import pl.gralewicz.kamil.java.app.bookingguide.service.ServiceService;
import pl.gralewicz.kamil.java.app.bookingguide.service.ShopService;
import pl.gralewicz.kamil.java.app.bookingguide.service.UserService;
import pl.gralewicz.kamil.java.app.bookingguide.service.VisitService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.logging.Logger;

@Controller // przyjmuje dane od użytkownika i zwraca dane do użytkownika za pomocą protokołu http
@RequestMapping(value = "/visits")
public class VisitController {
    private static final Logger LOGGER = Logger.getLogger(VisitController.class.getName());

    private VisitService visitService;
    private ServiceService serviceService;
    private ShopService shopService;
    private UserService userService;

    public VisitController(VisitService visitService, ServiceService serviceService, ShopService shopService, UserService userService) {
        this.visitService = visitService;
        this.serviceService = serviceService;
        this.shopService = shopService;
        this.userService = userService;
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
    public String createView(String username, ModelMap modelMap) {
        LOGGER.info("createView()");
        User userByUsername = userService.findByUsername(username);
        if (userByUsername == null) {
            modelMap.addAttribute("error", "User not found");
            return "visit-create";
        }
        List<Service> services = serviceService.list();
        List<Shop> shops = shopService.list();
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
        shopId = 602L;
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
        newVisit.setDueDate(LocalDate.parse(dueDate, DateTimeFormatter.ISO_DATE).atStartOfDay());

        Visit createdVisit = visitService.create(newVisit);
        LOGGER.info("createX(...)= " + createdVisit);
        return "redirect:/visits";
    }

    @PostMapping(value = "/create/user")
    public String createWithUsername(@RequestBody String username, ModelMap modelMap) {
        LOGGER.info("createWithUsername(" + username + ")");
        User userByUsername = userService.findByUsername(username);
        if (userByUsername == null) {
            modelMap.addAttribute("error", "User not found");
            return "visit-create";
        }
        List<Service> services = serviceService.list();
        List<Shop> shops = shopService.list();
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
    public String createWithService(@PathVariable(name = "id") Long serviceId, ModelMap modelMap) {
        LOGGER.info("createWithService(" + serviceId + ")");
        Service service = serviceService.read(serviceId);

        Visit visit = new Visit();
        visit.setService(service);

        LOGGER.info("createWithService(" + visit + ")");

        modelMap.addAttribute("visit", visit);
        modelMap.addAttribute("isEdit", false);
        LOGGER.info("createWithService(...)= ");
        return "visit-create.html";
    }

    @PostMapping
    public String create(@ModelAttribute Visit visit) {
        LOGGER.info("create(" + visit + ")");
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
        LOGGER.info("updateView()");
        Visit readVisit = visitService.read(id);
        modelMap.addAttribute("visit", readVisit);
        modelMap.addAttribute("isEdit", true);
        LOGGER.info("updateView(...)= " + readVisit);
        return "visit-create.html";
    }

    @GetMapping(value = "/delete/{id}")
    public String delete(@PathVariable Long id, ModelMap modelMap) {
        LOGGER.info("delete(" + id + ")");
        visitService.delete(id);
        return "redirect:/visits";
    }
}
