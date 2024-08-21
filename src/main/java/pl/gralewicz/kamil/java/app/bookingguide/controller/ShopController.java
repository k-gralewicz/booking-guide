package pl.gralewicz.kamil.java.app.bookingguide.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.logging.Logger;

@Controller
@RequestMapping(value = "/shops")
public class ShopController {
    private static Logger LOGGER = Logger.getLogger(ShopController.class.getName());

    @GetMapping
    public String method(String name) {
        LOGGER.info("method(" + name + ")");

        LOGGER.info("method(...)= ");
        return "shop-dashboard";
    }
}

// TODO: 21.08.2024 co to są servlety, przeczytać, nauczyć się