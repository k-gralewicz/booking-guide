package pl.gralewicz.kamil.java.app.bookingguide.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.gralewicz.kamil.java.app.bookingguide.controller.model.DurationType;
import pl.gralewicz.kamil.java.app.bookingguide.controller.model.Service;
import pl.gralewicz.kamil.java.app.bookingguide.service.ServiceService;

import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Logger;

@Controller
@RequestMapping(value = "/services")
public class ServiceController {
    private static final Logger LOGGER = Logger.getLogger(ServiceController.class.getName());

    private ServiceService serviceService;

    public ServiceController(ServiceService serviceService) {
        this.serviceService = serviceService;
    }

    @GetMapping
    public String list(ModelMap modelMap) {
        LOGGER.info("list()");
        List<Service> services = serviceService.list();
        modelMap.addAttribute("services", services);
        LOGGER.info("list(...)= " + services);
        return "services";
    }

    @GetMapping(value = "/create")
    public String createView(ModelMap modelMap) {
        LOGGER.info("createView()");
        modelMap.addAttribute("createMessage", "Fill out the form fields");
        modelMap.addAttribute("service", new Service());
        modelMap.addAttribute("durationTypes", DurationType.values());
        modelMap.addAttribute("isEdit", false);
        LOGGER.info("createView(...)= ");
        return "service-create";
    }

    @PostMapping
    public String create(@ModelAttribute Service service, BindingResult bindingResult, ModelMap modelMap) {
        LOGGER.info("Attempting to create service: " + service);
        if (bindingResult.hasErrors()) {
            LOGGER.warning("Binding errors occurred: " + bindingResult.getAllErrors());
            modelMap.addAttribute("createMessage", "Please correct the errors below.");
            modelMap.addAttribute("durationTypes", DurationType.values());
            modelMap.addAttribute("isEdit", false);
            return "service-create";
        }
        try {
            Service createdService = serviceService.create(service);
            LOGGER.info("Successfully created service: " + createdService);
            return "redirect:/services";
        } catch (Exception e) {
            LOGGER.severe("Error saving service: " + e.getMessage());
            modelMap.addAttribute("createMessage", "Error saving service: " + e.getMessage());
            modelMap.addAttribute("durationTypes", DurationType.values());
            modelMap.addAttribute("isEdit", false);
            modelMap.addAttribute("service", service);
            return "service-create";
        }
    }

    @GetMapping(value = "/id")
    public String read(@PathVariable Long id, String name, String description, BigDecimal price, int duration, DurationType durationType, ModelMap modelMap) {
        LOGGER.info("read(" + id + ")");
        LOGGER.info("read(" + name + ")");
        LOGGER.info("read(" + description + ")");
        LOGGER.info("read(" + price + ")");
        LOGGER.info("read(" + duration + ")");
        LOGGER.info("read(" + durationType + ")");
        Service readService = serviceService.read(id);
        modelMap.addAttribute("createMessage", "This is service: " + readService);
        LOGGER.info("read(...)= ");
        return "service-read.html";
    }

    @GetMapping(value = "/update/{id}")
    public String updateView(@PathVariable Long id, ModelMap modelMap) {
        LOGGER.info("updateView()");
        Service readService = serviceService.read(id);
        LOGGER.info("updateView(...)= " + readService);
        return "service-create";
    }

    @GetMapping(value = "/delete/{id}")
    public String delete(@PathVariable Long id, String name, String description, BigDecimal price, int duration, DurationType durationType, ModelMap modelMap) {
        LOGGER.info("read(" + id + ")");
        LOGGER.info("read(" + name + ")");
        LOGGER.info("read(" + description + ")");
        LOGGER.info("read(" + price + ")");
        LOGGER.info("read(" + duration + ")");
        LOGGER.info("read(" + durationType + ")");
        serviceService.delete(id);
        return "redirect:/services";
    }
}
