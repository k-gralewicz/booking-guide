package pl.gralewicz.kamil.java.app.bookingguide.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.gralewicz.kamil.java.app.bookingguide.controller.model.Client;
import pl.gralewicz.kamil.java.app.bookingguide.controller.model.Visit;
import pl.gralewicz.kamil.java.app.bookingguide.service.ClientService;
import pl.gralewicz.kamil.java.app.bookingguide.service.VisitService;

import java.util.List;
import java.util.logging.Logger;

@Controller
@RequestMapping(value="/clients")
public class ClientController {
    private static final Logger LOGGER = Logger.getLogger(ClientController.class.getName());

    private ClientService clientService;
    private VisitService visitService;

    public ClientController(ClientService clientService, VisitService visitService) {
        this.clientService = clientService;
        this.visitService = visitService;
    }

    @GetMapping(value="/dashboard")
    public String dashboard(ModelMap modelMap){
        LOGGER.info("dashboard()");
        List<Visit> visits = visitService.list();
        modelMap.addAttribute("visits", visits);
        LOGGER.info("dashboard(...)= " + visits);
        return "client-dashboard";
    }

    @GetMapping
    public String list(ModelMap modelMap){
        LOGGER.info("list()");
        List<Client> clients = clientService.list();
        modelMap.addAttribute("clients", clients);
        LOGGER.info("list(...)= " + clients);
        return "clients";
    }

    @GetMapping(value="/create")
    public String createView(ModelMap modelMap){
        LOGGER.info("createView()");
        modelMap.addAttribute("createMassage", "Fill out the form fields");
        modelMap.addAttribute("client", new Client());
        LOGGER.info("createView(...)= ");
        return "client-create";
    }

    @PostMapping
    public String create(@ModelAttribute Client client){
        LOGGER.info("create(" + client + ")");
        Client createdClient = clientService.create(client);
        LOGGER.info("create(...)= " + createdClient);
        return "redirect:/clients";
    }

    @GetMapping(value = "/id")
    public String read(@PathVariable Long id, String firstName, String lastName, String email, String phoneNumber, String address, ModelMap modelMap) {
        LOGGER.info("read("+ id +")");
        LOGGER.info("read("+ firstName +")");
        LOGGER.info("read("+ lastName +")");
        LOGGER.info("read("+ email +")");
        LOGGER.info("read("+ phoneNumber +")");
        LOGGER.info("read("+ address +")");
        Client readClient = clientService.read(id);
        modelMap.addAttribute("createMessage", "This is client: " + readClient);
        LOGGER.info("read(...)= ");
        return "client-read.html";
    }

    @GetMapping(value = "/update/{id}")
    public String updateView(@PathVariable Long id, ModelMap modelMap) {
        LOGGER.info("updateView()");
        Client readClient = clientService.read(id);
        LOGGER.info("updateView(...)= " + readClient);
        return "client-create";
    }

    @GetMapping(value = "/delete/{id}")
    public String delete(@PathVariable Long id, String firstName, String lastName, String email, String phoneNumber, String address, ModelMap modelMap) {
        LOGGER.info("delete("+ id +")");
        LOGGER.info("delete("+ firstName +")");
        LOGGER.info("delete("+ lastName +")");
        LOGGER.info("delete("+ email +")");
        LOGGER.info("delete("+ phoneNumber +")");
        LOGGER.info("delete("+ address +")");
        clientService.delete(id);
        return "redirect:/clients";
    }
}
// TODO: 21.08.2024 poprawić controller