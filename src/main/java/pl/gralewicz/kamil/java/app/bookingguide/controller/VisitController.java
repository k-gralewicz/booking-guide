package pl.gralewicz.kamil.java.app.bookingguide.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.gralewicz.kamil.java.app.bookingguide.controller.model.Service;
import pl.gralewicz.kamil.java.app.bookingguide.controller.model.Visit;
import pl.gralewicz.kamil.java.app.bookingguide.service.ServiceService;
import pl.gralewicz.kamil.java.app.bookingguide.service.VisitService;

import java.util.List;
import java.util.logging.Logger;

@Controller
@RequestMapping(value = "/visits")
public class VisitController {
    private static final Logger LOGGER = Logger.getLogger(VisitController.class.getName());

    private VisitService visitService;
    private ServiceService serviceService;

    public VisitController(VisitService visitService, ServiceService serviceService) {
        this.visitService = visitService;
        this.serviceService= serviceService;
    }

    @GetMapping
    public String list(ModelMap modelMap) {
        LOGGER.info("list()");
//        List<Visit> visits = new ArrayList<>();
//
//        Visit firstVisit = new Visit();
//        firstVisit.setId(1L);
//        visits.add(firstVisit);
//
//        Visit secondVisit = new Visit();
//        secondVisit.setId(2L);
//        visits.add(secondVisit);

        List<Visit> visits = visitService.list();
        modelMap.addAttribute("visits", visits);
        LOGGER.info("list(...)= " + visits);
        return "visits";
    }

    @GetMapping(value = "/create/{id}")
    public String createView(@PathVariable(name = "id") Long serviceId,ModelMap modelMap) {
        LOGGER.info("createView(" + serviceId + ")" );
        Service service = serviceService.read(serviceId);

        Visit visit = new Visit();
        visit.setService(service);

        LOGGER.info("creteView("+ visit +")");

        modelMap.addAttribute("createMassage", "Fill out the form fields");
        modelMap.addAttribute("visit", visit);
        modelMap.addAttribute("isEdit", false);
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

    @GetMapping(value = "/{id}")
    public String read(@PathVariable Long id, ModelMap modelMap) {
        LOGGER.info("read(" + id + ")");
        Visit readVisit = visitService.read(id);
        modelMap.addAttribute("createMessage", "This is visit: " + readVisit);
        boolean isEdit = true;
        modelMap.addAttribute("isEdit", isEdit);
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
