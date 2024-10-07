package pl.gralewicz.kamil.java.app.bookingguide.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.gralewicz.kamil.java.app.bookingguide.controller.model.Client;
import pl.gralewicz.kamil.java.app.bookingguide.controller.model.Service;
import pl.gralewicz.kamil.java.app.bookingguide.controller.model.Shop;
import pl.gralewicz.kamil.java.app.bookingguide.controller.model.Visit;
import pl.gralewicz.kamil.java.app.bookingguide.service.VisitService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Controller
@RequestMapping(value = "/visits")
public class VisitController {
    private static final Logger LOGGER = Logger.getLogger(VisitController.class.getName());

    private VisitService visitService;

    public VisitController(VisitService visitService) {
        this.visitService = visitService;
    }

    @GetMapping
    public String list(ModelMap modelMap) {
        LOGGER.info("list()");
        List<Visit> visits = new ArrayList<>();
        visits.add(new Visit());
        visits.add(new Visit());
        modelMap.addAttribute("visits", visits);
        LOGGER.info("list(...)= " + visits);
        return "visits";
    }

    @GetMapping(value = "/create")
    public String createView(ModelMap modelMap) {
        LOGGER.info("createView()");
        modelMap.addAttribute("createMassage", "Fill out the form fields");
        modelMap.addAttribute("visit", new Visit());
        LOGGER.info("createView(...)= ");
        return "visit-create.html";
    }

    @PostMapping
    public String create(@ModelAttribute Visit visit) {
        LOGGER.info("create(" + visit + ")");
        Visit createdVisit = visitService.create(visit);
        LOGGER.info("create(...)= " + createdVisit);
        return "redirect:/visits";
    }

    @GetMapping(value = "/id")
    public String read(@PathVariable Long id, Client client, Service service, Shop shop, LocalDateTime dateDate, ModelMap modelMap) {
        LOGGER.info("read(" + id + ")");
        LOGGER.info("read(" + client + ")");
        LOGGER.info("read(" + service + ")");
        LOGGER.info("read(" + shop + ")");
        LOGGER.info("read(" + dateDate + ")");
        Visit readVisit = visitService.read(id);
        modelMap.addAttribute("createMessage", "This is visit: " + readVisit);
        LOGGER.info("read(...)= ");
        return "visit-read.html";
    }

    @GetMapping(value = "/update/{id}")
    public String updateView(@PathVariable Long id, ModelMap modelMap) {
        LOGGER.info("updateView()");
        Visit readVisit = visitService.read(id);
        LOGGER.info("updateView(...)= " + readVisit);
        return "visit-create.html";
    }

    @GetMapping(value = "/delete/{id}")
    public String delete(@PathVariable Long id, Client client, Service service, Shop shop, LocalDateTime dateDate, ModelMap modelMap) {
        LOGGER.info("delete(" + id + ")");
        LOGGER.info("delete(" + client + ")");
        LOGGER.info("delete(" + service + ")");
        LOGGER.info("delete(" + shop + ")");
        LOGGER.info("delete(" + dateDate + ")");
        visitService.delete(id);
        return "redirect:/visits";
    }
}
