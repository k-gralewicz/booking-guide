package pl.gralewicz.kamil.java.app.bookingguide.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.gralewicz.kamil.java.app.bookingguide.controller.model.Client;
import pl.gralewicz.kamil.java.app.bookingguide.service.ClientService;

import java.util.logging.Logger;

@Controller
@RequestMapping(value="/clients")
public class ClientController {
    private static final Logger LOGGER = Logger.getLogger(ClientController.class.getName());

    private ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping(value="/create")
    public String createView(){
        LOGGER.info("createView()");

        LOGGER.info("createView(...)= ");
        return "client-create";
    }

    @PostMapping
    public String create(Client client){
        LOGGER.info("create(" + client + ")");
        Client createdClient = clientService.create(client);
        LOGGER.info("create(...)= " + createdClient);
        return "client-create";
    }


//    public Client create(Client client) throws Exception {
//        LOGGER.info("create()");
//        if (client != null) {
//            if (client.getLastName() == null) {
//                LOGGER.warning("Please provide last name for the client");
//                throw new Exception("Please provide last name for the client");
//            }
//        }
//
//        Client createdClient = clientService.create(client);
//        LOGGER.info("create(...)=" + createdClient);
//        return createdClient;
//    }
}
// TODO: 21.08.2024 poprawić controller